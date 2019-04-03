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
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Currency;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author D070483
 */
@Stateless
public class CurrencyBean extends EntityBean<Currency, Long>{

    public CurrencyBean() {
        super(Currency.class);
    }
    
     public List<Currency> findAllSorted() {
        return this.em.createQuery("SELECT p FROM Currency p ORDER BY p.name").getResultList();
    }
     
    public List<Currency> findAllByUser(User owner) {
        return this.em.createQuery("SELECT p FROM Currency p wHERE p.owner = :owner ORDER BY p.name")
                .setParameter("owner", owner)
                .getResultList();
    }
    
}
