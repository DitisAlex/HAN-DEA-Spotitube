package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class PlaylistDAOTest {
    private final int PLAYLISTID = 1;
    private final String PLAYLISTNAME = "Test Song";
    private final String OWNER = "test";
    private final int LENGTH = 100;

    private PlaylistDAO playlistDAO;
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

        playlistDAO = new PlaylistDAO();
        playlistDAO.setDataSource(dataSource);
    }

    /**
     * DAO FOR:
     * [GET] /playlists
     */
    @Test
    public void getAllPlaylists() {
        try {
            // Arrange
            String expectedSQL = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t " +
                    "INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.playlistID ) AS duration " +
                    "FROM playlist AS p " +
                    "LEFT OUTER JOIN playlist_tracks AS pt " +
                    "ON p.id = pt.playlistID";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(PLAYLISTID);
            when(resultSet.getString("name")).thenReturn(PLAYLISTNAME);
            when(resultSet.getString("owner")).thenReturn(OWNER);
            when(resultSet.getInt("duration")).thenReturn(LENGTH);

            // Act
            List<Playlist> playlists = playlistDAO.getPlaylists();

            Playlist playlistMock = mock(Playlist.class);
            playlistMock.setId(PLAYLISTID);
            playlistMock.setName(PLAYLISTNAME);
            playlistMock.setOwner(OWNER);
            playlistMock.setLength(LENGTH);

            playlists.add(playlistMock);

            // Assert
            verify(connection).prepareStatement(expectedSQL); // Verifies connection
            verify(preparedStatement).executeQuery();         // & SQL statement execution

            assertEquals(resultSet.getInt("id"), PLAYLISTID); // Checks if expected is same as result
            assertEquals(resultSet.getString("name"), PLAYLISTNAME);
            assertEquals(resultSet.getString("owner"), OWNER);
            assertEquals(resultSet.getInt("duration"), LENGTH);

        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * DAO FOR:
     * [DELETE] /playlists/{id}
     */
    @Test
    public void deletePlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "DELETE FROM playlist WHERE id = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            playlistDAO.deletePlaylist(PLAYLISTID);

            // Assert
            verify(connection).prepareStatement(expectedSQL); // Verifies connection, SQL execution & set parameter
            verify(preparedStatement).setInt(1, PLAYLISTID);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * DAO FOR:
     * [POST] /playlists
     */
    @Test
    public void addPlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlist (name, owner) VALUES (?, ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            playlistDAO.addPlaylist(PLAYLISTNAME, OWNER);

            // Assert
            verify(connection).prepareStatement(expectedSQL); // Verifies connection, SQL execution & set parameter
            verify(preparedStatement).setString(1, PLAYLISTNAME);
            verify(preparedStatement).setString(2, OWNER);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * DAO FOR:
     * [PUT] /playlists/{id}
     */
    @Test
    public void updatePlaylistTest() {
        try {
            // Arrange
            String expectedSQL = "UPDATE playlist SET name = ? WHERE id = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            playlistDAO.updatePlaylist(PLAYLISTNAME, PLAYLISTID);

            // Assert
            verify(connection).prepareStatement(expectedSQL); // Verifies connection, SQL execution & set parameter
            verify(preparedStatement).setString(1, PLAYLISTNAME);
            verify(preparedStatement).setInt(2, PLAYLISTID);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e) {
            fail(e);
        }
    }
}
