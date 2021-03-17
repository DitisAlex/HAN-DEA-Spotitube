package han.oose.dea.service;

import han.oose.dea.dao.ITokenDAO;
import han.oose.dea.dao.IUserDAO;
import han.oose.dea.domain.Token;
import han.oose.dea.domain.User;
import han.oose.dea.exceptions.UnauthorizedException;
import han.oose.dea.service.dto.TokenDTO;
import han.oose.dea.service.dto.UserDTO;

import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {

    private LoginService loginService;
    private UserDTO userDTO;
    private TokenDTO tokenDTO;

    @BeforeEach
    public void setup(){
        loginService = new LoginService();
        userDTO = new UserDTO();
        tokenDTO = new TokenDTO();
    }

    /**
     * Happy Path - valid username & password combination
     * [POST] /login
     */
    @Test
    public void loginSuccessTest(){

        // Arrange:
        int statuscodeExpected = 200;

        User user = new User();
        Token token = new Token();

        userDTO.user = "test";
        userDTO.password = "test";

        IUserDAO userDAOMock = mock(IUserDAO.class);
        ITokenDAO tokenDAOMock = mock(ITokenDAO.class);

        when(userDAOMock.checkAuthenticated(userDTO.user, userDTO.password)).thenReturn(user);
        when(tokenDAOMock.addTokenToDB(tokenDTO.token, userDTO.user)).thenReturn(token);

        loginService.setUserDAO(userDAOMock);
        loginService.setTokenDAO(tokenDAOMock);
        Response response = null;

        // Act:
                try {
                    response = loginService.login(userDTO);
                } catch (UnauthorizedException e){
                    fail();
                }
        TokenDTO tokenDTO = (TokenDTO) response.getEntity();

        // Assert:
        assertEquals(statuscodeExpected, response.getStatus()); //Check status code
        assertEquals(user.getUsername(), tokenDTO.user); //Check username
    }

    /**
     * Unhappy path - invalid username & password combination
     * [POST] /login
     */
    @Test
    public void loginFailTest(){

        // Arrange:
        IUserDAO userDAOMock = mock(IUserDAO.class);
        when(userDAOMock.checkAuthenticated(null, null)).thenReturn(null);
        loginService.setUserDAO(userDAOMock);

        // Assert:
        assertThrows(UnauthorizedException.class, () -> {
            loginService.login(userDTO);
        });
    }
}
