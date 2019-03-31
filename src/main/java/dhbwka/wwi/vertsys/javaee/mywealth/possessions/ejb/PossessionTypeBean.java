/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb;

import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.EntityBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import dhbwka.wwi.vertsys.javaee.mywealth.tasks.jpa.Category;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author timba
 */
@Stateless
@RolesAllowed("app-user")
public class PossessionTypeBean extends EntityBean<PossessionType, Long> {
    
    public PossessionTypeBean() {
        super(PossessionType.class);
    }
    
     public List<PossessionType> findAllSorted() {
        return this.em.createQuery("SELECT p FROM PossessionType p ORDER BY p.name").getResultList();
    }
     
    public List<PossessionType> findAllByUser(User owner) {
        return this.em.createQuery("SELECT p FROM PossessionType p wHERE p.owner = :owner ORDER BY p.name")
                .setParameter("owner", owner)
                .getResultList();
    }
    
}
