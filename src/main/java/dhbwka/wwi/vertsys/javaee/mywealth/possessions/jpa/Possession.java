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
 * Datenbankklasse für ein Besitztum des Nutzers
 *
 * @author Jonas Strube
 */
@Entity
@Table(name = "MYWEALTH_POSSESSION")
public class Possession {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "possession_ids")
    @TableGenerator(name = "possession_ids", initialValue = 0, allocationSize = 50)
    private long id;

    @ManyToOne
    @NotNull(message = "Das Besitztum muss einem Nutzer gehören.")
    private User owner;

    private String name;

    @ManyToOne
    @NotNull(message = "Das Besitztum muss einer Kategorie zugeordnet sein.")
    private Possessiontype type;

    
    @NotNull(message = "Das Besitztum muss einen Wert haben.")
    private int valueInEuro;

    private String comments;

    
    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Possession() {
    }
    
    

    public Possession(long id, User owner, String name, Possessiontype type, int valueInEuro, String comments) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.type = type;
        this.valueInEuro = valueInEuro;
        this.comments = comments;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Possessiontype getType() {
        return type;
    }

    public void setType(Possessiontype type) {
        this.type = type;
    }

    public int getValueInEuro() {
        return valueInEuro;
    }

    public void setValueInEuro(int valueInEuro) {
        this.valueInEuro = valueInEuro;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    //</editor-fold>

    
}
