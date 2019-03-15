/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 zarpator
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa;

import dhbwka.wwi.vertsys.javaee.mywealth.common.jpa.User;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
/**
 *
 * @author David Scheid
 */
@Entity
@Table(name = "MYWEALTH_CURRENCY")
public class Currency {
    
@Id
private long id;

@NotNull(message = "Die Währung muss einen Namen haben.")
private String name;

@NotNull(message = "Die Währung muss einen Umrechnungsfaktor haben.")
private double conversionRate;

//<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Currency() {
    }
    
    

    public Currency(long id, String name, double conversionRate) {
        this.id = id;
        this.name = name;
        this.conversionRate = conversionRate;
    }
    //</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(double conversionRate) {
        this.conversionRate = conversionRate;
    }

    //</editor-fold>

}
