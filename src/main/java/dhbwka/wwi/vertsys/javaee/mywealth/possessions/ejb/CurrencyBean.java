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
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Currency;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.Possession;
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
    
    // TODO define order of the results (template is commented out)
    public List<Currency> findByUser(String username){
        return em.createQuery("SELECT e FROM Currency e WHERE e.owner.username = :username" /**ORDER BY t.dueDate, t.dueTime"**/)
                            .setParameter("username", username)
                            .getResultList();
    }
    
    public Currency createNewCurrency(String name, double conversionRate) {
        Currency currency = new Currency(name, conversionRate);
        em.persist(currency);
        return em.merge(currency);
    }
    
    
}
