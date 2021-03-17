package han.oose.dea.dao;

import han.oose.dea.domain.Token;

public interface ITokenDAO {
    Token addTokenToDB(String token, String username);
    String verifyToken(String token);
}
