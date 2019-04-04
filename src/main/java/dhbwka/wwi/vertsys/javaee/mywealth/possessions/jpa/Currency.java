/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
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
@GeneratedValue(strategy = GenerationType.TABLE, generator = "currency_ids")
@TableGenerator(name = "currency_ids", initialValue = 0, allocationSize = 50)
private long id;

@NotNull(message = "Die Währung muss einen Namen haben.")
private String name;

@NotNull(message = "Die Währung muss einen Umrechnungsfaktor haben.")
private double conversionRate;

@ManyToOne
User owner;

//<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Currency() {
    }
    
    

    public Currency(String name, double conversionRate, User owner) {
        this.name = name;
        this.conversionRate = conversionRate;
        this.owner = owner;
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
    
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    //</editor-fold>

//Umrechnen von Währungen    
public double calculateValue(double valueInEuro) {
    return valueInEuro/conversionRate;
}

public double convertToEuro(double valueInCurrency){
    return valueInCurrency*conversionRate;
}
}
