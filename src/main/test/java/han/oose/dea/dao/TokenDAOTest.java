package han.oose.dea.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class TokenDAOTest {

    private final String USERNAME = "test";
    private final String TOKEN = "123456";

    private TokenDAO tokenDAO;
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() {
        dataSource = mock(DataSource.class);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        tokenDAO = new TokenDAO();
        tokenDAO.setDataSource(dataSource);
    }

    /**
     * DAO FOR:
     * [POST] /login
     */
    @Test
    public void addTokenToUserDBTest() {
        try {
            // Arrange
            String expectedSQL = "UPDATE users SET token = ? WHERE username = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            tokenDAO.addTokenToDB(TOKEN, USERNAME);

            // Assert
            verify(connection).prepareStatement(expectedSQL); // Verifies connection, SQL execution & set parameter
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).setString(2, USERNAME);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * DAO FOR:
     * Login/Playlist services
     */
    @Test
    public void verifyTokenTest() {
        try {
            // Arrange
            String expectedSQL = "SELECT * FROM users WHERE token = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true);
            when(resultSet.getString("username")).thenReturn(USERNAME);

            // Act
            String result = tokenDAO.verifyToken(TOKEN);

            // Assert
            verify(connection).prepareStatement(expectedSQL); // Verifies connection, SQL execution & set parameter
            verify(preparedStatement).setString(1, TOKEN);
            verify(preparedStatement).executeQuery();

            assertEquals(USERNAME, result);
        } catch (Exception e) {
            fail(e);
        }
    }
}