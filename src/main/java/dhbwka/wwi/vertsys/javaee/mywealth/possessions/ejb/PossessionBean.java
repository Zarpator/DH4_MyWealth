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
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
    
    public List<Possession> search(String search, PossessionType type) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Possession t
        CriteriaQuery<Possession> query = cb.createQuery(Possession.class);
        Root<Possession> from = query.from(Possession.class);
        query.select(from);

        // ORDER BY dueDate, dueTime
        //query.orderBy(cb.asc(from.get("dueDate")), cb.asc(from.get("dueTime")));
        
        // WHERE t.shortText LIKE :search
        Predicate p = cb.conjunction();
        
        //if (search != null && !search.trim().isEmpty()) {
        //    p = cb.and(p, cb.like(from.get("shortText"), "%" + search + "%"));
        //    query.where(p);
        //}
        
        // WHERE t.category = :category
        if (type != null) {
            p = cb.and(p, cb.equal(from.get("type"), type));
            query.where(p);
        }
        
        return em.createQuery(query).getResultList();
    }
    
}
