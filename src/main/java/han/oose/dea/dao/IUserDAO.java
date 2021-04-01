package han.oose.dea.dao;

import han.oose.dea.domain.User;
import han.oose.dea.exceptions.ForbiddenException;
import han.oose.dea.exceptions.UnauthorizedException;

public interface IUserDAO {

    User checkAuthenticated(String username, String password) throws ForbiddenException, UnauthorizedException;
}
