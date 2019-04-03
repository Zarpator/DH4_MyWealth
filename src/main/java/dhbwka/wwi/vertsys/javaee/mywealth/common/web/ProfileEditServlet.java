/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.common.web;

import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.UserBean.InvalidCredentialsException;
import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.jpa.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author timba
 */
@WebServlet(name = "ProfileEditServlet", urlPatterns = {"/app/profile/edit"})
public class ProfileEditServlet extends HttpServlet {
    private static boolean changed = false;
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Prüfe ob der Nutzer bereits versucht hat seine Daten zu ändern, aber die Validation fehl schlug.
        //Die Eingaben die den Fehler verursachten sollten auch bei einem Reload in den Feldern bleiben, der status ist "changed=true"
        //Ist der User neu auf der Seite, werden alle Benutzerdaten aus der Datenbank gezogen und es ist "changed=false"
        if(!changed){  
           //Prefill FormValues object with User Data
          FormValues formValues = new FormValues();
          User user = this.userBean.getCurrentUser();
          String[] arrayUsername = {user.getUsername()};
          String[] arrayFirstname = {user.getFirstname()};
          String[] arrayLastname = {user.getLastname()};
          HashMap<String, String[]> userData = new HashMap<String, String[]>();
          userData.put("username", arrayUsername);
          userData.put("firstname", arrayFirstname);
          userData.put("lastname", arrayLastname);        
          formValues.setValues(userData);
           
          //set the prefill-values at the request 
          request.setAttribute("userdata_form", formValues);
        }
       changed=false;
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userVerwaltung/profile_edit.jsp");
        dispatcher.forward(request, response);
        
        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("userdata_form");
       
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }
        
        switch (action) {
            case "changeData":
                changed=true;
                this.changeData(request, response);
                break;
            case "changePassword":
                changed=true;
                this.changePassword(request, response);
                break;
        }
          
        }
        
       
      
       
    
    private void changeData(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         //Werte aus der Request holen
         String username = request.getParameter("username");
         String firstname = request.getParameter("firstname");
         String lastname = request.getParameter("lastname");
         
          String[] arrayFirstname = {firstname};
          String[] arrayLastname = {lastname};
         
         if(firstname.trim().equals("")){
            firstname=null;
        }
        if(lastname.trim().equals(""))
            lastname=null;
        
        //Dem aktuellen User die übergebenen Werte setzen 
        User user = this.userBean.getCurrentUser();
        
        user.setFirstname(firstname);
        user.setLastname(lastname);
       
        List<String> errors = this.validationBean.validate(user);
        //this.validationBean.validate(user.getPassword(), errors);
        
        //Wenn keine Fehler User updaten, wenn Fehler Formular erneut füllen
        if(errors.isEmpty()){
             this.userBean.update(user);
             changed=false;
             response.sendRedirect(request.getRequestURI().substring(0, 22));
        }else {
            // Fehler: Formular erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            HttpSession session = request.getSession();
            session.setAttribute("userdata_form", formValues);
            response.sendRedirect(request.getRequestURI());
            
       
             
       }
    }
 
    
    
    private void changePassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        //Passwörter aus Request lesen
        String password1 = request.getParameter("password1");
         String password2 = request.getParameter("password2");
         
         //dummy erzeugen um mit validate(Password,errors) das Passwort zu checken
         User dummy = new User("", password1, "", "");
         
         List<String> errors = new ArrayList<String>();
        this.validationBean.validate(dummy.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
       User currentUser = this.userBean.getCurrentUser();
       
       //Wenn keine Fehler Passwort setzen und User updaten, ansonsten Formular erneut füllen
        if(errors.isEmpty()){  
            currentUser.setPassword(password1);
            this.userBean.update(currentUser);
                changed=false;
                response.sendRedirect(request.getRequestURI().substring(0, 22));
        }else {
            // Fehler: Formular erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();        
            session.setAttribute("userdata_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
            
       }
         
        
         
         
         
         
    }

 
}
