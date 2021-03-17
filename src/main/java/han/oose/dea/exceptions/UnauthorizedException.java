package han.oose.dea.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnauthorizedException extends Exception implements ExceptionMapper<UnauthorizedException> {
    public UnauthorizedException(){
        super("Unauthorized: Invalid username/password");
    }

    @Override
    public Response toResponse(UnauthorizedException e){
        return Response.status(401).entity(e.getMessage()).build();
    }
}
