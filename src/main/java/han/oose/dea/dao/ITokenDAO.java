package han.oose.dea.dao;

import han.oose.dea.domain.User;
import han.oose.dea.exceptions.ForbiddenException;
import han.oose.dea.exceptions.UnauthorizedException;

public interface ITokenDAO {
    User addTokenToDB(String token, String username) throws UnauthorizedException, ForbiddenException;
    String verifyToken(String token) throws ForbiddenException;
}
