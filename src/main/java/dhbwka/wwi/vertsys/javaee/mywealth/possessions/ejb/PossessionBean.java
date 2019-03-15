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
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author Jonas Strube
 */
@Stateless
@RolesAllowed("app-user")
public class PossessionBean extends EntityBean<Possession, Long>{
    
    public PossessionBean() {
        super(Possession.class);
    }
    
    // TODO define order of the results (template is commented out)
    public List<Possession> findByUser(String username){
        return em.createQuery("SELECT p FROM Possession p WHERE p.owner.username = :username" /**ORDER BY t.dueDate, t.dueTime"**/)
                 .setParameter("username", username)
                 .getResultList();
    }
    
}
