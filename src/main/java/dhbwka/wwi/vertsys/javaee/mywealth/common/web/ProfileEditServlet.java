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
import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.jpa.User;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    @EJB
    UserBean userBean;
    
    @EJB
    ValidationBean validationBean;
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
         request.setAttribute("userdata_form", formValues);

         
            
        
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userVerwaltung/profile_edit.jsp");
        dispatcher.forward(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
         String username = request.getParameter("username");
         String firstname = request.getParameter("firstname");
         String lastname = request.getParameter("lastname");
         String password1 = request.getParameter("password1");
         String password2 = request.getParameter("password2");
         
         if(firstname.trim().equals("")){
            firstname=null;
        }
        if(lastname.trim().equals(""))
            lastname=null;
       
           
        User user = this.userBean.getCurrentUser();
        
        user.setFirstname(firstname);
        user.setLastname(lastname);
       
        List<String> errors = this.validationBean.validate(user);
        this.validationBean.validate(user.getPassword(), errors);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        if(errors.isEmpty()){
             this.userBean.update(user);
                response.sendRedirect(request.getRequestURI().substring(0, 22));
        }else {
            // Fehler: Formular erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.removeAttribute("userdata_form");
            session.setAttribute("userdata_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }
        
        
       
       
       
      
       
    }

 
}
