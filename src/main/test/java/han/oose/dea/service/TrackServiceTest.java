package han.oose.dea.service;

import han.oose.dea.dao.ITrackDAO;
import han.oose.dea.domain.Track;
import han.oose.dea.service.dto.TrackDTO;
import han.oose.dea.service.dto.TracksDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class TrackServiceTest {
    private final String TOKEN = "1234TOKEN";
    private final int PLAYLISTID = 2;
    private final int TRACKID = 1;
    private final String TITLE = "Test Title";
    private final String PERFORMER = "Test Performer";
    private final int DURATION = 123;
    private final String ALBUM = "Test Album";
    private final int PLAYCOUNT = 5;
    private final String PUBLICATIONDATE = "01-02-2021";
    private final String DESCRIPTION = "Test Description";
    private final boolean OFFLINEAVAILABLE = true;

    private TrackService trackService;
    private TrackService trackServiceMock;

    private ITrackDAO iTrackDAO;
    private TrackDTO trackDTO;
    private TracksDTO tracksDTO;

    @BeforeEach
    public void setup() {
        iTrackDAO = mock(ITrackDAO.class);

        Track track = new Track();
        track.setId(TRACKID);
        track.setTitle(TITLE);
        track.setPerformer(PERFORMER);
        track.setDuration(DURATION);
        track.setAlbum(ALBUM);
        track.setPlaycount(PLAYCOUNT);
        track.setPublicationDate(PUBLICATIONDATE);
        track.setDescription(DESCRIPTION);
        track.setOfflineAvailable(OFFLINEAVAILABLE);

        trackDTO = new TrackDTO();
        tracksDTO = new TracksDTO();

        trackDTO.id = track.getId();
        trackDTO.title = track.getTitle();
        trackDTO.performer = track.getPerformer();
        trackDTO.duration = track.getDuration();
        trackDTO.album = track.getAlbum();
        trackDTO.playcount = track.getPlaycount();
        trackDTO.publicationDate = track.getPublicationDate();
        trackDTO.description = track.getDescription();
        trackDTO.offlineAvailable = track.getOfflineAvailable();

        tracksDTO.tracks = new ArrayList<>();
        tracksDTO.tracks.add(trackDTO);

        trackService = new TrackService();
        trackServiceMock = Mockito.spy(trackService);
        trackServiceMock.setTrackDAO(iTrackDAO);
    }

    /**
     * [GET] /tracks
     */
    @Test
    public void getAllTracksTest() {
        // Arrange
        int expectedStatuscode = 200;

        doReturn(tracksDTO).when(trackServiceMock).listToDTOConverter(TRACKID, true);

        // Act
        Response response = trackServiceMock.getTracks(PLAYLISTID, TOKEN);

        // Assert
        assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
    }

    /**
     * [GET] /playlists/{id}/tracks
     */
    @Test
    public void getTracksFromPlaylistTest() {
        // Arrange
        int expectedStatuscode = 200;

        doReturn(tracksDTO).when(trackServiceMock).listToDTOConverter(TRACKID, false);

        // Act
        Response response = trackServiceMock.getTracksFromPlaylist(PLAYLISTID, TOKEN);

        // Assert
        assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
    }

    /**
     * [DELETE] /playlists/{playlistID}/tracks/{trackID}
     */
    @Test
    public void removeTrackFromPlaylistTest() {
        // Arrange
        int expectedStatuscode = 200;

        doReturn(tracksDTO).when(trackServiceMock).listToDTOConverter(TRACKID, false);

        // Act
        Response response = trackServiceMock.removeTrackFromPlaylist(PLAYLISTID, TRACKID, TOKEN);

        // Assert
        assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
    }

    /**
     * [PUT] /playlists/{id}/tracks
     */
    @Test
    public void addTrackToPlaylist() {
        // Arrange
        int expectedStatuscode = 201;

        doReturn(tracksDTO).when(trackServiceMock).listToDTOConverter(TRACKID, false);

        // Act
        Response response = trackServiceMock.addTrackToPlaylist(trackDTO, TRACKID, TOKEN);

        // Assert
        assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
    }
}
