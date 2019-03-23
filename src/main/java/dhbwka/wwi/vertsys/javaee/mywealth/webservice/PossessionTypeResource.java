/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.webservice;

import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionTypeBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
@Path("PossessionTypes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PossessionTypeResource {
    
    @EJB
    private PossessionTypeBean possessionTypeBean;
    
    
    // <editor-fold defaultstate="collapsed" desc="Zugriff auf die Collection">
    /**
     * GET /api/PossessionTypes/
     * Auslesen einer Liste von Musikstücken.
     */
    @GET
    public List<PossessionType> findPossessionTypes() {
        return this.possessionTypeBean.findAll();
    }

    /**
     * POST /api/PossessionTypes/
     * Speichern eines neuen Songs.
     */
    @POST
    public PossessionType saveNewPossessionType(@Valid PossessionType possessionType) {
        return this.possessionTypeBean.saveNew(possessionType);
    }
   
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Zugriff auf einzelne Ressourcen">
    /**
     * GET /api/PossessionTypes/{id}/
     * Auslesen eines einzelnen Songs anhand seiner ID.
     */
    @GET
    @Path("{id}")
    public PossessionType getPossessionType(@PathParam("id") long id) {
        return this.possessionTypeBean.findById(id);
    }

    /**
     * PUT /api/PossessionTypes/{id}/
     * Aktualisieren eines vorhandenen Songs.
     */
    @PUT
    @Path("{id}")
    public PossessionType updatePossessionType(@PathParam("id") long id, @Valid PossessionType possessionType) {
        possessionType.setId(id);
        return this.possessionTypeBean.update(possessionType);
    }

    /**
     * DELETE /api/PossessionTypes/{id}/
     * Löschen eines vorhandenen Songs.
     */
    @DELETE
    @Path("{id}")
    public PossessionType deletePossessionType(@PathParam("id") long id) {
        PossessionType possessionType = this.possessionTypeBean.findById(id);

        if (possessionType != null) {
            this.possessionTypeBean.delete(possessionType);
        }

        return possessionType;
    }
// </editor-fold>

}
