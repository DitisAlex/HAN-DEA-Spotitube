package han.oose.dea.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenException extends Exception implements ExceptionMapper<ForbiddenException> {
    public ForbiddenException(){
        super("Forbidden: Invalid token");
    }

    @Override
    public Response toResponse(ForbiddenException e){
        return Response.status(403).entity(e.getMessage()).build();
    }
}
