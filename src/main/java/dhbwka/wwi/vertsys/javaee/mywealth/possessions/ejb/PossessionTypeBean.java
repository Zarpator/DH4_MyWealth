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
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;

/**
 *
 * @author timba
 */
public class PossessionTypeBean extends EntityBean<PossessionType, Long> {
    
    public PossessionTypeBean(Class<PossessionType> entityClass) {
        super(entityClass);
    }
    
    
    
}
