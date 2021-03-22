package han.oose.dea.service;

import han.oose.dea.dao.IPlaylistDAO;
import han.oose.dea.dao.ITokenDAO;
import han.oose.dea.domain.Playlist;
import han.oose.dea.exceptions.ForbiddenException;
import han.oose.dea.service.dto.PlaylistDTO;
import han.oose.dea.service.dto.PlaylistsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {
    private final int PLAYLISTID = 1;
    private final String PLAYLISTNAME = "Test Song";
    private final boolean ISOWNER = true;
    private final String OWNER = "Test Owner";
    private final String TOKEN = "token1234";
    private final String USER = "Test";
    private final int LENGTH = 123;

    private PlaylistService playlistService;
    private PlaylistService playlistServiceMock;

    private ITokenDAO iTokenDAO;
    private IPlaylistDAO iPlaylistDAO;
    private PlaylistsDTO playlistsDTO;
    private PlaylistDTO playlistDTO;


    @BeforeEach()
    public void setup() {
        iPlaylistDAO = mock(IPlaylistDAO.class);
        iTokenDAO = mock(ITokenDAO.class);

        Playlist playlist = new Playlist();
        playlist.setId(PLAYLISTID);
        playlist.setName(PLAYLISTNAME);
        playlist.setOwner(OWNER);
        playlist.setLength(LENGTH);

        playlistsDTO = new PlaylistsDTO();
        playlistDTO = new PlaylistDTO();

        playlistDTO.id = playlist.getId();
        playlistDTO.name = playlist.getName();
        playlistDTO.owner = ISOWNER;
        playlistsDTO.playlists = new ArrayList<>();
        playlistsDTO.length = playlist.getLength();
        playlistsDTO.playlists.add(playlistDTO);

        playlistService = new PlaylistService();
        playlistServiceMock = Mockito.spy(playlistService);
        playlistServiceMock.setPlaylistDAO(iPlaylistDAO);
        playlistServiceMock.setTokenDAO(iTokenDAO);
    }

    /**
     * [GET] /playlists
     */
    @Test
    public void getAllPlaylistsTest() {
        try {
            // Arrange
            int expectedStatuscode = 200;

            when(iTokenDAO.verifyToken(TOKEN)).thenReturn(USER);
            doReturn(playlistsDTO).when(playlistServiceMock).listToDTOConverter(USER);

            // Act
            Response response = playlistServiceMock.getAllPlaylists(TOKEN);
            PlaylistsDTO responsePlaylistsDTO = (PlaylistsDTO) response.getEntity();

            // Assert
            assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
            assertEquals(playlistsDTO, responsePlaylistsDTO); // Checks playlistsDTO body
        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * [POST] /playlists
     */
    @Test
    public void createNewPlaylistTest() {
        try {
            // Arrange
            int expectedStatuscode = 201;

            when(iTokenDAO.verifyToken(TOKEN)).thenReturn(USER);
            doNothing().when(iPlaylistDAO).addPlaylist(playlistDTO.name, USER);
            doReturn(playlistsDTO).when(playlistServiceMock).listToDTOConverter(USER);

            // Act
            Response response = playlistServiceMock.createNewPlaylist(playlistDTO, TOKEN);

            // Assert
            assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * [DELETE] /playlists/{id}
     */
    @Test
    public void deletePlaylistTest() {
        try {
            // Arrange
            int expectedStatuscode = 200;

            when(iTokenDAO.verifyToken(TOKEN)).thenReturn(USER);
            doNothing().when(iPlaylistDAO).deletePlaylist(playlistDTO.id);
            doReturn(playlistsDTO).when(playlistServiceMock).listToDTOConverter(USER);

            // Act
            Response response = playlistServiceMock.deletePlaylist(PLAYLISTID, TOKEN);

            // Assert
            assertEquals(expectedStatuscode, response.getStatus()); // Checks status code
        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * [PUT] /playlists/{id}
     */
    @Test
    public void updatePlaylistTest() {
        try {
            // Arrange
            int expectedStatuscode = 200;

            when(iTokenDAO.verifyToken(TOKEN)).thenReturn(USER);
            doNothing().when(iPlaylistDAO).updatePlaylist(playlistDTO.name, PLAYLISTID);
            doReturn(playlistsDTO).when(playlistServiceMock).listToDTOConverter(USER);

            // Act
            Response response = playlistServiceMock.updatePlaylist(playlistDTO, PLAYLISTID, TOKEN);

            // Assert
            assertEquals(expectedStatuscode, response.getStatus()); // Check status code
        } catch (Exception e) {
            fail(e);
        }
    }

    /**
     * [POST] /playlists/
     * Checks error handling of all methods
     */
    @Test
    public void invalidTokenPlaylistTest() {
        assertThrows(ForbiddenException.class, () -> { // Checks if ForbiddenException error is thrown
            playlistServiceMock.getAllPlaylists(null);
        });
    }
}