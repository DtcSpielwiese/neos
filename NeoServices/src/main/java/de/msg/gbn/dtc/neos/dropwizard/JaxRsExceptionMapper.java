package de.msg.gbn.dtc.neos.dropwizard;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JaxRsExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception ex) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        return Response.status(statusCode)
                .entity(new ExceptionHolder(statusCode, ex.getMessage()))
                .type("application/problem+json")
                .build();
    }

    public class ExceptionHolder {
        private int code;
        private String message;

        public ExceptionHolder(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}
