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
        
        request.getRequestDispatcher("/WEB-INF/possessions/possession_edit.jsp").forward(request, response);
    }
    
    // wird aufgerufen wenn auf der possession_edit.jsp ein Button zum Hinzufügen, Bearbeiten oder Löschen einer Possession geklickt wird
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        //TODO implement form confirmation
    }    
}
