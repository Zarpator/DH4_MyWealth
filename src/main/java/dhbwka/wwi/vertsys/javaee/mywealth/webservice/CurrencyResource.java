/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.webservice;

import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.CurrencyBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Currency;
import java.util.List;
import javax.ejb.EJB;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author timba
 */
@Path("Currencies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CurrencyResource {
    
    @EJB
    private CurrencyBean currencyBean;
    
    @EJB
    UserBean userBean;
    
    
    // <editor-fold defaultstate="collapsed" desc="Zugriff auf die Collection">
    /**
     * GET /api/Currencies/
     * Auslesen einer Liste von Musikstücken.
     * @return 
     */
    @GET
    public List<Currency> findCurrencies(){
        return this.currencyBean.findAllByUser(this.userBean.getCurrentUser());
    }

    /**
     * POST /api/Currencies/
     * Speichern eines neuen Songs.
     * @param currency
     * @return 
     */
    @POST
    public Currency saveNewCurrency(@Valid Currency currency) {
        return this.currencyBean.saveNew(currency);
    }
   
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Zugriff auf einzelne Ressourcen">
    /**
     * GET /api/Currencies/{id}/
     * Auslesen eines einzelnen Songs anhand seiner ID.
     * @param id
     * @return 
     */
    @GET
    @Path("{id}")
    public Currency getCurrency(@PathParam("id") long id) {
        return this.currencyBean.findById(id);
    }

    /**
     * PUT /api/Currencies/{id}/
     * Aktualisieren eines vorhandenen Songs.
     * @param id
     * @param currency
     * @return 
     */
    @PUT
    @Path("{id}")
    public Currency updateCurrency(@PathParam("id") long id, @Valid Currency currency) {
        currency.setId(id);
        return this.currencyBean.update(currency);
    }

    /**
     * DELETE /api/Currencies/{id}/
     * Löschen eines vorhandenen Songs.
     * @param id
     * @return 
     */
    @DELETE
    @Path("{id}")
    public Currency deleteCurrency(@PathParam("id") long id) {
        Currency currency = this.currencyBean.findById(id);

        if (currency != null) {
            this.currencyBean.delete(currency);
        }

        return currency;
    }
// </editor-fold>

}
