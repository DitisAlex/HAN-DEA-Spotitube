package han.oose.dea.service;

import han.oose.dea.dao.ITokenDAO;
import han.oose.dea.dao.IUserDAO;
import han.oose.dea.domain.User;
import han.oose.dea.exceptions.UnauthorizedException;
import han.oose.dea.service.dto.TokenDTO;
import han.oose.dea.service.dto.UserDTO;
import han.oose.dea.util.TokenGenerator;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("")
public class LoginService {

    private IUserDAO iUserDAO;
    private ITokenDAO iTokenDAO;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO userDTO) throws UnauthorizedException {
        User user = iUserDAO.checkAuthenticated(userDTO.user, userDTO.password);

        if(user == null) throw new UnauthorizedException();

        TokenDTO tokenDTO = new TokenDTO();

        TokenGenerator tokenGenerator = new TokenGenerator();
        String token = tokenGenerator.generateToken();
        iTokenDAO.addTokenToDB(token, user.getUsername());

        tokenDTO.user = user.getUsername();
        tokenDTO.token = token;
        return Response.status(200).entity(tokenDTO).build();
    }

    @Inject
    public void setUserDAO(IUserDAO iUserDAO) {
        this.iUserDAO = iUserDAO;
    }

    @Inject
    public void setTokenDAO(ITokenDAO iTokenDAO){
        this.iTokenDAO = iTokenDAO;
    }
}
