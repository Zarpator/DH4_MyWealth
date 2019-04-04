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
@WebServlet(name = "CurrencyListServlet", urlPatterns = {"/app/possessions/currencies/"})
public class CurrencyListServlet extends HttpServlet {

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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/possessions/currency_list.jsp");
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
                this.createCurrency(request, response);
                break;
            case "delete":
                this.deleteCurrencies(request, response);
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
    private void createCurrency(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");
        String conversionRateString = request.getParameter("conversionRate");
        double conversionRate = Double.parseDouble(conversionRateString);
        
        Currency currency = new Currency(name, conversionRate, this.userBean.getCurrentUser());
        
        List<String> errors = this.validationBean.validate(currency);

        // Neue Währung anlegen
        if (errors.isEmpty()) {
            this.currencyBean.saveNew(currency);
            HttpSession session = request.getSession();
            if(session.getAttribute("currency_form")!=null)
                session.removeAttribute("currency_form");
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("currency_form", formValues);
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
    private void deleteCurrencies(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Währung IDs auslesen
        String[] currencyIds = request.getParameterValues("currency");
         List<String> errors = new ArrayList<>();

        if (currencyIds == null) {
            
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            errors.add("Sie müssen zuerst etwas markieren um zu löschen");
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("currency_form", formValues);
        
            currencyIds = new String[0];
        }
        if(errors.isEmpty()){
             // Types löschen
             for (String currencyId : currencyIds) {
            
            
               // Zu löschende Währung ermitteln
              Currency currency;

               try {
                    currency = this.currencyBean.findById(Long.parseLong(currencyId));
                } catch (NumberFormatException ex) {
                     continue;
                }

               
               if (currency == null) {
                      continue;
                 }

/*              // Bei allen betroffenen Besitztümer, den Bezug zur Type aufheben
             List<Possession> possessions = possessionType.getPossessions();

              if (possessions != null) {
                 possessions.forEach((Possession possession) -> {
                     possession.setType(null);
                        this.possessionBean.update(possession);
                    });
             }
*/
            // Und weg damit
            this.currencyBean.delete(currency);
            
            HttpSession session = request.getSession();
            if(session.getAttribute("currency_form")!=null)
                session.removeAttribute("currency_form");
         }
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }



}
