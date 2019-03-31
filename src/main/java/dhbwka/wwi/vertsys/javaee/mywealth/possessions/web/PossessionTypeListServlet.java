/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.web;

import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.CurrencyBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionTypeBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Currency;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import dhbwka.wwi.vertsys.javaee.mywealth.tasks.jpa.Category;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "PossessionTypeListServlet", urlPatterns = {"/app/possessions/possessiontypes/"})
public class PossessionTypeListServlet extends HttpServlet {

    @EJB
    PossessionTypeBean possessiontypeBean;
    
    @EJB
    UserBean userBean;

    @EJB
    PossessionBean possessionBean;

    @EJB
    ValidationBean validationBean;
    
    @EJB
    CurrencyBean currencyBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Anlagetypen ermitteln
        request.setAttribute("possessiontypes", this.possessiontypeBean.findAllByUser(this.userBean.getCurrentUser()));
        request.setAttribute("currencies", this.currencyBean.findAll());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/possessions/possessionType_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("possessiontypes_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createPossessionType(request, response);
                break;
            case "delete":
                this.deletePossessionTypes(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Kategorie anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createPossessionType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");
        String currencyName = request.getParameter("currencySelection");
        
        Currency currency = this.currencyBean.findById(Long.parseLong(currencyName));
        if(name.trim()=="")
            name=null;
        PossessionType possessionType = new PossessionType(name, currency, this.userBean.getCurrentUser());
        
        List<String> errors = this.validationBean.validate(possessionType);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.possessiontypeBean.saveNew(possessionType);
            HttpSession session = request.getSession();
            if(session.getAttribute("possessiontype_form")!=null)
                session.removeAttribute("possessiontype_form");
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("possessiontype_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Kategorien löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deletePossessionTypes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] possessionTypeIds = request.getParameterValues("possessiontype");
         List<String> errors = new ArrayList<>();

        if (possessionTypeIds == null) {
            
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            errors.add("Sie müssen zuerst etwas markieren um zu löschen");
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("possessiontype_form", formValues);
        
            possessionTypeIds = new String[0];
        }
        if(errors.isEmpty()){
             // Types löschen
             for (String possessionTypeId : possessionTypeIds) {
            
            
               // Zu löschender Type ermitteln
              PossessionType possessionType;

               try {
                    possessionType = this.possessiontypeBean.findById(Long.parseLong(possessionTypeId));
                } catch (NumberFormatException ex) {
                     continue;
                }

               
               if (possessionType == null) {
                      continue;
                 }

              // Bei allen betroffenen Besitztümer, den Bezug zur Type aufheben
             List<Possession> possessions = possessionType.getPossessions();

              if (possessions != null) {
                 possessions.forEach((Possession possession) -> {
                     possession.setType(null);
                        this.possessionBean.update(possession);
                    });
             }

            // Und weg damit
            this.possessiontypeBean.delete(possessionType);
            
            HttpSession session = request.getSession();
            if(session.getAttribute("possessiontype_form")!=null)
                session.removeAttribute("possessiontype_form");
         }
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }



}
