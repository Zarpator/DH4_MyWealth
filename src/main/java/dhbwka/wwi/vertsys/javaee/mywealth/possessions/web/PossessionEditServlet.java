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
import dhbwka.wwi.vertsys.javaee.mywealth.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mywealth.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.mywealth.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionTypeBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet zum Hinzufügen, Löschen und Bearbeiten von Possessions
 *
 * @author Jonas Strube
 */
@WebServlet(urlPatterns = "/app/possessions/possession/*")
public class PossessionEditServlet extends HttpServlet {

    @EJB
    private PossessionBean possessionBean;

    @EJB
    private PossessionTypeBean possessionTypeBean;

    @EJB
    private UserBean userBean;

    @EJB
    private ValidationBean validationBean;

    // wird aufgerufen wenn die Seite zum Hinzufügen oder Bearbeiten von Possessions geladen wird
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errors = new ArrayList<>();

        // get Possession from id in request url
        Possession possession = this.getPossession(request, errors);

        request.setAttribute("possession", possession);
        request.setAttribute("poss_types", this.possessionTypeBean.findAllSorted());
        
        // set if the possession is new or getting edited 
        request.setAttribute("edit", possession.getId() != 0);

        if (!errors.isEmpty()){
            request.getRequestDispatcher("/WEB-INF/login/error.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/possessions/possession_edit.jsp").forward(request, response);
        }
    }

    // wird aufgerufen wenn auf der possession_edit.jsp ein Button zum Hinzufügen, Bearbeiten oder Löschen einer Possession geklickt wird
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.savePossession(request, response);
                break;
            case "delete":
                this.deletePossession(request, response);
                break;
        }
    }

    private void savePossession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String possName = request.getParameter("poss_name");
        String possType = request.getParameter("poss_type");
        String possValue = request.getParameter("poss_value");
        String possComments = request.getParameter("poss_comments");

        Possession possession = this.getPossession(request, errors);

        if (possType != null && !possType.trim().isEmpty()) {
            try {
                possession.setType(this.possessionTypeBean.findById(Long.parseLong(possType)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }

        if (possValue != null || !possValue.isEmpty()) {
            possession.setValueInEuro(Integer.parseInt(possValue));
        } else {
            errors.add("Das Besitztum muss einen Wert haben.");
        }

        possession.setName(possName);
        possession.setComments(possComments);

        this.validationBean.validate(possession, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.possessionBean.update(possession);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/possessions/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("task_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    private Possession getPossession(HttpServletRequest request, List<String> errors) {
        
        Possession possession = new Possession();
        User currentUser = this.userBean.getCurrentUser();
        possession.setOwner(currentUser);
        
        String id = request.getPathInfo();

        if (id == null) {
            id = "";
        }

        id = id.substring(1);

        if (id.endsWith("/")) {
            id = id.substring(0, id.length() - 1);
        }
        
        try {
            Possession found_possession = this.possessionBean.findById(Long.parseLong(id));
            
            // check if the logged user is allowed to see the found possession
            if (found_possession != null && found_possession.getOwner().getUsername().equals(currentUser.getUsername())){
                possession = found_possession;
            } else {
                errors.add("Ein Besitztum mit dieser ID gehört nicht zu deinem Besitz.");
            }
        } catch (NumberFormatException e) {
            // ID ist nicht vorhanden oder kann nicht in Long konvertiert werden
            // Tritt ein wenn neue Possession hinzugefügt wird
            // Denn dann wird keine ID sondern "new" mitgegeben
        }

        return possession;
    }
    
    private void deletePossession(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> errors = new ArrayList<>();
        
        // Datensatz löschen
        Possession possession = this.getPossession(request, errors);
        this.possessionBean.delete(possession);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/possessions/list/"));
    }
}
