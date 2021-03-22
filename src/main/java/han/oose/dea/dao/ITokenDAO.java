package han.oose.dea.dao;

import han.oose.dea.domain.User;

public interface ITokenDAO {
    User addTokenToDB(String token, String username);
    String verifyToken(String token);
}
