/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author timba
 */
@Entity
@Table(name = "MYWEALTH_POSSESSIONTYPE")
public class PossessionType {
     @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "possessionType_ids")
    @TableGenerator(name = "possessionType_ids", initialValue = 0, allocationSize = 50)
    private long id;

    
    @NotNull(message = "Der Anlagetyp darf nicht leer sein.")
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    List<Possession> possessions = new ArrayList<>();
    
    @ManyToOne
    Currency currency;
    
    
      //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public PossessionType() {
    }

    public PossessionType(String name) {
        this.name = name;
       // this.currency = currency;
    }
    
     public PossessionType(String name, Currency currency) {
        this.name = name;
        this.currency = currency;
       
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

    public List<Possession> getPossessions() {
        return possessions;
    }

    public void setPossessions(List<Possession> possessions) {
        this.possessions = possessions;
    }
    
    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    //</editor-fold>
}
