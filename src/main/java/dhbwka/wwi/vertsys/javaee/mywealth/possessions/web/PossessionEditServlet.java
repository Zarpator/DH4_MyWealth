/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.web;

import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet zum Hinzufügen, Löschen und Bearbeiten von Possessions
 * @author Jonas Strube
 */

@WebServlet(urlPatterns = "/app/possessions/edit/*")
public class PossessionEditServlet extends HttpServlet {
    
    @EJB
    private PossessionBean possessionBean;
    
    // wird aufgerufen wenn die Seite zum Hinzufügen oder Bearbeiten von Possessions geladen wird
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO implement call of website
        
        // id handling test
        String id = request.getPathInfo();
        id = id.substring(1);
        request.setAttribute("poss_id", id);

        // TODO add possession_form to the request (like in taskeditservlet)
        
        // get Possession from id in request url
        // Possession possession = this.getPossession(request);
        // request.setAttribute("possession-form", possession_form);
        
        request.getRequestDispatcher("/WEB-INF/possessions/possession_edit.jsp").forward(request, response);
    }
    
    // wird aufgerufen wenn auf der possession_edit.jsp ein Button zum Hinzufügen, Bearbeiten oder Löschen einer Possession geklickt wird
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        //TODO implement form confirmation
    }    

    private Possession getPossession(HttpServletRequest request) {
        Possession possession = new Possession();
        
        String id = request.getPathInfo();
        
        if (id == null) {
            id = "";
        }
        
        id = id.substring(1);
        
        if (id.endsWith("/")) {
            id = id.substring(0, id.length() - 1);
        }
        
        try {
            possession = this.possessionBean.findById(Long.parseLong(id));
        } catch (NumberFormatException e){
            // ID ist nicht vorhanden oder kann nicht in Long konvertiert werden
        }
        
        return possession;
    }
}
