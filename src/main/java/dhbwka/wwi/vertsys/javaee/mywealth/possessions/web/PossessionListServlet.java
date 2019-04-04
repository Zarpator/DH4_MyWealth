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
import dhbwka.wwi.vertsys.javaee.mywealth.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.CurrencyBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionTypeBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Currency;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jspclasses.PossessionForListJSP;
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
 * @author Jonas Strube
 */
@WebServlet(urlPatterns = {"/app/possessions/list/"})
public class PossessionListServlet extends HttpServlet{
    
    @EJB
    private PossessionBean possessionBean;
    
    @EJB
    private PossessionTypeBean possessionTypeBean;
    
    @EJB
    private CurrencyBean currencyBean;
    
    @EJB
    private UserBean userBean;
    
    // wird aufgerufen wenn die Seite zum Ansehen der Possessions geladen wird
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        
        // set searched possessionType
        String search_type = request.getParameter("search_possessionType");
        PossessionType type = null;
        if (search_type != null && !search_type.isEmpty()){
            type = possessionTypeBean.findById(Long.parseLong(search_type));
        }
        
        // set current user
        User owner = userBean.getCurrentUser();
        
        List<Possession> possessionsFromBean = possessionBean.search(owner, type);
        List<PossessionForListJSP> possessions = this.getPossessionsForJSP(possessionsFromBean);
        
        //Possessions an den Request anhängen
        request.setAttribute("possessions", possessions);

        // Request an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/possessions/possession_list.jsp").forward(request, response);
    }
    
    // calculate and get all values needed for the list jsp
    private List<PossessionForListJSP> getPossessionsForJSP(List<Possession> possessionsFromEJB){
        List<PossessionForListJSP> possessions = new ArrayList();
        
        for (Possession possessionFromEJB : possessionsFromEJB){
            PossessionForListJSP possession = new PossessionForListJSP();
            
            possession.setName(possessionFromEJB.getName());
            possession.setId(possessionFromEJB.getId());
            possession.setTyp(possessionFromEJB.getType().getName());
            possession.setValueInEuro(possessionFromEJB.getValueInEuro());
            possession.setComments(possessionFromEJB.getComments());
            
            possession.setCurrencyName(possessionFromEJB.getType().getCurrency().getName());
            
            Currency currency = currencyBean.findById(possessionFromEJB.getType().getCurrency().getId());
            possession.setVaueInMyCurrency(currency.calculateValue(possessionFromEJB.getValueInEuro()));
            
            possessions.add(possession);
        }
        
        return possessions;
    }
}
