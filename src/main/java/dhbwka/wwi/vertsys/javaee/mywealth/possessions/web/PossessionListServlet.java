/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 zarpator
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.web;

import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Auflistung der Possessions des Users.
 * @author zarpator
 */
@WebServlet(urlPatterns = {"/app/possessions/list/"})
public class PossessionListServlet extends HttpServlet{
    
    @EJB
    private PossessionBean possessionBean;
    
    // wird aufgerufen wenn die Seite zum Ansehen der Possessions geladen wird
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        
        // alle Possessions des Users auslesen
        String username = request.getUserPrincipal().getName();
        List<Possession> possessions = possessionBean.findByUser(username);
        
        //Possessions an den Request anhängen
        request.setAttribute("possessions", possessions);

        // Request an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/possessions/possession_list.jsp").forward(request, response);
    }
}
