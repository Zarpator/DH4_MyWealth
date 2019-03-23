/*
 * Copyright © 2019 David Scheid
 * Copyright © 2019 Jonas Strube
 * Copyright © 2019 Tim Bayer
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.webservice;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author timba
 */
/**
 * JAX-RS Exception Mapper für beliebige Exceptions. Dieser sorgt dafür, dass
 * bei Auftreten einer Exception dennoch eine ordentliche Antwort an den Client
 * gesendet wird.
 */
@Provider
public class RestExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        ExceptionResponse result = new ExceptionResponse();
        result.exception = ex.getClass().getName();
        result.message = ex.getMessage();

        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

}