package han.oose.dea.dao;

import han.oose.dea.domain.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrackDAOTest {
    private final int PLAYLISTID = 5;
    private final int TRACKID = 1;
    private final String TITLE = "Test Title";
    private final String PERFORMER = "Test Performer";
    private final int DURATION = 500;
    private final String ALBUM = "Test Album";
    private final int PLAYCOUNT = 10;
    private final String PUBLICATIONDATE = "01-02-2021";
    private final String DESCRIPTION = "Test description";
    private final boolean OFFLINEAVAILABLE = true;

    private TrackDAO trackDAO;
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

        trackDAO = new TrackDAO();
        trackDAO.setDataSource(dataSource);
    }

    @Test
    public void getPlaylistTracksTest(){
        try {
            // Arrange
            String expectedSQL = "SELECT * FROM tracks t " +
                    "LEFT JOIN playlist_tracks pt " +
                    "ON t.id = pt.trackID " +
                    "WHERE pt.playlistID = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(TRACKID);
            when(resultSet.getString("title")).thenReturn(TITLE);
            when(resultSet.getString("performer")).thenReturn(PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(DURATION);
            when(resultSet.getString("album")).thenReturn(ALBUM);
            when(resultSet.getInt("playcount")).thenReturn(PLAYCOUNT);
            when(resultSet.getString("publicationDate")).thenReturn(PUBLICATIONDATE);
            when(resultSet.getString("description")).thenReturn(DESCRIPTION);
            when(resultSet.getBoolean("offlineAvailable")).thenReturn(OFFLINEAVAILABLE);

            // Act
            List<Track> tracks = trackDAO.getTracks(TRACKID, false);

            Track trackMock = mock(Track.class);
            trackMock.setId(TRACKID);
            trackMock.setTitle(TITLE);
            trackMock.setPerformer(PERFORMER);
            trackMock.setDuration(DURATION);
            trackMock.setAlbum(ALBUM);
            trackMock.setPlaycount(PLAYCOUNT);
            trackMock.setPublicationDate(PUBLICATIONDATE);
            trackMock.setDescription(DESCRIPTION);
            trackMock.setOfflineAvailable(OFFLINEAVAILABLE);

            tracks.add(trackMock);

            // Arrange
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

            assertEquals(resultSet.getInt("id"), TRACKID);
            assertEquals(resultSet.getString("title"), TITLE);
            assertEquals(resultSet.getString("performer"), PERFORMER);
            assertEquals(resultSet.getInt("duration"), DURATION);
            assertEquals(resultSet.getString("album"), ALBUM);
            assertEquals(resultSet.getInt("playcount"), PLAYCOUNT);
            assertEquals(resultSet.getString("publicationDate"), PUBLICATIONDATE);
            assertEquals(resultSet.getString("description"), DESCRIPTION);
            assertEquals(resultSet.getBoolean("offlineAvailable"), OFFLINEAVAILABLE);

        } catch (Exception e){
            fail(e);
        }
    }

    @Test
    public void getAddTracksTest(){
        try {
            // Arrange
            String expectedSQL = "SELECT DISTINCT * FROM tracks t " +
                    "LEFT JOIN playlist_tracks pt " +
                    "ON t.id = pt.trackID " +
                    "WHERE t.id NOT IN (SELECT trackID from playlist_tracks WHERE playlistID = ?) " +
                    "GROUP BY t.id";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true).thenReturn(false);
            when(resultSet.getInt("id")).thenReturn(TRACKID);
            when(resultSet.getString("title")).thenReturn(TITLE);
            when(resultSet.getString("performer")).thenReturn(PERFORMER);
            when(resultSet.getInt("duration")).thenReturn(DURATION);
            when(resultSet.getString("album")).thenReturn(ALBUM);
            when(resultSet.getInt("playcount")).thenReturn(PLAYCOUNT);
            when(resultSet.getString("publicationDate")).thenReturn(PUBLICATIONDATE);
            when(resultSet.getString("description")).thenReturn(DESCRIPTION);
            when(resultSet.getBoolean("offlineAvailable")).thenReturn(OFFLINEAVAILABLE);

            // Act
            List<Track> tracks = trackDAO.getTracks(TRACKID, true);

            Track trackMock = mock(Track.class);
            trackMock.setId(TRACKID);
            trackMock.setTitle(TITLE);
            trackMock.setPerformer(PERFORMER);
            trackMock.setDuration(DURATION);
            trackMock.setAlbum(ALBUM);
            trackMock.setPlaycount(PLAYCOUNT);
            trackMock.setPublicationDate(PUBLICATIONDATE);
            trackMock.setDescription(DESCRIPTION);
            trackMock.setOfflineAvailable(OFFLINEAVAILABLE);

            tracks.add(trackMock);

            // Arrange
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).executeQuery();

            assertEquals(resultSet.getInt("id"), TRACKID);
            assertEquals(resultSet.getString("title"), TITLE);
            assertEquals(resultSet.getString("performer"), PERFORMER);
            assertEquals(resultSet.getInt("duration"), DURATION);
            assertEquals(resultSet.getString("album"), ALBUM);
            assertEquals(resultSet.getInt("playcount"), PLAYCOUNT);
            assertEquals(resultSet.getString("publicationDate"), PUBLICATIONDATE);
            assertEquals(resultSet.getString("description"), DESCRIPTION);
            assertEquals(resultSet.getBoolean("offlineAvailable"), OFFLINEAVAILABLE);

        } catch (Exception e){
            fail(e);
        }
    }

    @Test
    public void removeTrackFromPlaylist(){
        try {
            // Arrange
            String expectedSQL = "DELETE FROM playlist_tracks WHERE trackID = ? AND playlistID = ?";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            trackDAO.removeTrackFromPlaylist(PLAYLISTID, TRACKID);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, TRACKID);
            verify(preparedStatement).setInt(2, PLAYLISTID);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e){
            fail(e);
        }
    }

    @Test
    public void addTrackToPlaylist(){
        try {
            // Arrange
            String expectedSQL = "INSERT INTO playlist_tracks (playlistID, trackID, offlineAvailable) VALUES (?, ?, ?)";

            when(dataSource.getConnection()).thenReturn(connection);
            when(connection.prepareStatement(expectedSQL)).thenReturn(preparedStatement);

            // Act
            trackDAO.addTrackToPlaylist(PLAYLISTID, TRACKID, OFFLINEAVAILABLE);

            // Assert
            verify(connection).prepareStatement(expectedSQL);
            verify(preparedStatement).setInt(1, PLAYLISTID);
            verify(preparedStatement).setInt(2, TRACKID);
            verify(preparedStatement).setBoolean(3, OFFLINEAVAILABLE);
            verify(preparedStatement).executeUpdate();

        } catch (Exception e){
            fail(e);
        }
    }
}
