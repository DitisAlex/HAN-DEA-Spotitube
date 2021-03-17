package han.oose.dea.dao;

import han.oose.dea.domain.User;

public interface IUserDAO {

    User checkAuthenticated(String username, String password);
}
