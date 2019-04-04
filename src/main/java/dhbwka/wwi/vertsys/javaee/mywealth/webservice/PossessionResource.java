/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.webservice;

import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
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
 * @author Jonas Strube
 */
@Path("Possessions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PossessionResource {
    @EJB
    PossessionBean possessionBean;
    
    @EJB
    UserBean userBean;
    
    
    // <editor-fold defaultstate="collapsed" desc="Zugriff auf die Collection">
    /**
     * GET /api/Possessions/
     * Auslesen einer Liste von Musikstücken.
     */
    @GET
    public List<Possession> findPossessions(){
        List<Possession> list = this.possessionBean.findByUser(this.userBean.getCurrentUser().getUsername());
        return list;
    }

    /**
     * POST /api/Possessions/
     * Speichern eines neuen Songs.
     */
    @POST
    public Possession saveNewPossession(@Valid Possession possessionType) {
        return this.possessionBean.saveNew(possessionType);
    }
   
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Zugriff auf einzelne Ressourcen">
    /**
     * GET /api/Possession/{id}/
     * Auslesen einer einzelnen Possession anhand ihrer ID.
     */
    @GET
    @Path("{id}")
    public Possession getPossession(@PathParam("id") long id) {
        return this.possessionBean.findById(id);
    }

    /**
     * PUT /api/Possessions/{id}/
     * Aktualisieren einer vorhandenen Possession.
     */
    @PUT
    @Path("{id}")
    public Possession updatePossession(@PathParam("id") long id, @Valid Possession possessionType) {
        possessionType.setId(id);
        return this.possessionBean.update(possessionType);
    }

    /**
     * DELETE /api/Possessions/{id}/
     * Löschen eines vorhandenen Songs.
     */
    @DELETE
    @Path("{id}")
    public Possession deletePossession(@PathParam("id") long id) {
        Possession possessionType = this.possessionBean.findById(id);

        if (possessionType != null) {
            this.possessionBean.delete(possessionType);
        }

        return possessionType;
    }
// </editor-fold>
}
