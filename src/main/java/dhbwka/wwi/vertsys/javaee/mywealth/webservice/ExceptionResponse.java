/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.webservice;

/**
 *
 * @author timba
 */ 

/**
 * Antwortobjekt, das bei einer Exception an den Client gesendet wird.
 */
public class ExceptionResponse {

    public String exception;
    public String message;

    // Ggf. weitere Daten, die wir an den Client senden wollen

}
