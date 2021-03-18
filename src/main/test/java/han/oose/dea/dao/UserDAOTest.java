package han.oose.dea.dao;

import han.oose.dea.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserDAOTest {

    private final String USERNAME = "test";
    private final String PASSWORD = "password";
    private final String TOKEN = "123456";

    private UserDAO userDAO;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup(){
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        userDAO = new UserDAO();
        userDAO.setDataSource(dataSource);
    }

    @Test
    public void checkAuthenticatedWithValidCredentialsTest(){
        try {
            // Arrange
            String expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("username")).thenReturn(USERNAME);
            when(resultSet.getString("token")).thenReturn(TOKEN);

            // Act
            User mockUser = new User();
            mockUser.setUsername(USERNAME);
            mockUser.setToken(TOKEN);
            User result = userDAO.checkAuthenticated(USERNAME, PASSWORD);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setString(1, USERNAME);
            verify(preparedStatement).setString(2, PASSWORD);
            verify(preparedStatement).executeQuery();

            assertEquals(mockUser.getUsername(), result.getUsername());
            assertEquals(mockUser.getToken(), result.getToken());

        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    public void checkAuthenticatedWithInvalidCredentialsTest(){
        try {
            // Arrange
            String expectedSQL = "SELECT * FROM users WHERE username = ? AND password = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(false);

            // Act
            User result = userDAO.checkAuthenticated(USERNAME, PASSWORD);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

            assertNull(result);

        } catch (Exception e) {
            fail(e);
        }
    }
}
