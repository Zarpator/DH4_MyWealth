/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.web;

import dhbwka.wwi.vertsys.javaee.mywealth.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.mywealth.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
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

/**
 * Servlet für die Auflistung der Possessions des Users.
 * @author D070381
 */
@WebServlet(urlPatterns = {"/app/possessions/list/"})
public class PossessionListServlet extends HttpServlet{
    
    @EJB
    private PossessionBean possessionBean;
    
    @EJB
    DashboardContentProvider taskContent;
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{        
        List<Possession> possessions = possessionBean.findByUser("jonas");
        request.setAttribute("possessions", possessions);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/possessions/possession_list.jsp").forward(request, response);
    }
}
