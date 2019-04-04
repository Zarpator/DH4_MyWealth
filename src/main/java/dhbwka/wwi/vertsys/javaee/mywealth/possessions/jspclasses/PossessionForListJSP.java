/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.possessions.jspclasses;

/**
 *
 * @author Jonas Strube
 */
public class PossessionForListJSP {
    private String name;
    private long id;
    private double valueInEuro;
    private double vaueInMyCurrency;
    private String currencyName;
    private String typ;
    private String comments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getValueInEuro() {
        return valueInEuro;
    }

    public void setValueInEuro(double valueInEuro) {
        this.valueInEuro = valueInEuro;
    }

    public double getVaueInMyCurrency() {
        return vaueInMyCurrency;
    }

    public void setVaueInMyCurrency(double vaueInMyCurrency) {
        this.vaueInMyCurrency = vaueInMyCurrency;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    
    
    
}
