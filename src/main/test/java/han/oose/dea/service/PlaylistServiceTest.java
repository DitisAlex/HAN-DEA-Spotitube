package han.oose.dea.service;

import han.oose.dea.dao.IPlaylistDAO;
import han.oose.dea.dao.ITokenDAO;
import han.oose.dea.dao.IUserDAO;
import han.oose.dea.domain.Playlist;
import han.oose.dea.domain.Token;
import han.oose.dea.domain.User;
import han.oose.dea.exceptions.ForbiddenException;
import han.oose.dea.exceptions.UnauthorizedException;
import han.oose.dea.service.dto.PlaylistDTO;
import han.oose.dea.service.dto.PlaylistsDTO;
import han.oose.dea.service.dto.TokenDTO;
import han.oose.dea.service.dto.UserDTO;

import javax.validation.constraints.Null;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlaylistServiceTest {

    private PlaylistService playlistService;
    private PlaylistDTO playlistDTO;
    private PlaylistsDTO playlistsDTO;
    private TokenDTO tokenDTO;

    @BeforeEach()
    public void setup(){
        playlistService = new PlaylistService();

        playlistDTO = new PlaylistDTO();
        playlistsDTO = new PlaylistsDTO();
        tokenDTO = new TokenDTO();

        playlistDTO.id = 1;
        playlistDTO.name = "Test Playlist";
        playlistDTO.owner = true;

        playlistsDTO.playlists = new ArrayList<>();
        playlistsDTO.playlists.add(playlistDTO);
        playlistsDTO.length = 123;

        tokenDTO.token = "deze1token2bestaat3niet4";

    }

    /**
     * Happy Path - gather all playlists successfully
     * [GET] /playlists
     */
    // @Test
    public void getAllPlaylistsInvalidTokenTest(){

        // Arrange
        IPlaylistDAO playlistDAOMock = mock(IPlaylistDAO.class);
        PlaylistService playlistServiceMock = mock(PlaylistService.class);
        when(playlistDAOMock.getPlaylists()).thenReturn(playlistsDTO.playlists);
        playlistService.setPlaylistDAO(playlistDAOMock);

        // Assert
        assertThrows(ForbiddenException.class, () -> {
            playlistService.getAllPlaylists(tokenDTO.token);
        });
    }

}
