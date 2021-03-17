package han.oose.dea.service;

import han.oose.dea.dao.IPlaylistDAO;
import han.oose.dea.dao.ITokenDAO;
import han.oose.dea.dao.PlaylistDAO;
import han.oose.dea.domain.Playlist;
import han.oose.dea.service.dto.PlaylistDTO;
import han.oose.dea.service.dto.PlaylistsDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("")
public class PlaylistService {

    private IPlaylistDAO iPlaylistDAO;
    private ITokenDAO iTokenDAO;

    @GET
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        return Response.status(200).entity(listToDTOConverter(iTokenDAO.verifyToken(token))).build();
    }

    @DELETE
    @Path("/playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        iPlaylistDAO.deletePlaylist(id);
        return Response.status(200).entity(listToDTOConverter(iTokenDAO.verifyToken(token))).build();
    }

    @POST
    @Path("/playlists")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewPlaylist(PlaylistDTO playlistDTO, @QueryParam("token") String token){
        String username = iTokenDAO.verifyToken(token);
        iPlaylistDAO.addPlaylist(playlistDTO.name, username);
        return Response.status(201).entity(listToDTOConverter(username)).build();
    }

    @PUT
    @Path("/playlists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlaylist(PlaylistDTO playlistDTO, @PathParam("id") int id, @QueryParam("token") String token) {
        iPlaylistDAO.updatePlaylist(playlistDTO.name, id);
        return Response.status(200).entity(listToDTOConverter(iTokenDAO.verifyToken(token))).build();
    }

    private PlaylistsDTO listToDTOConverter(String username){
        if(username == null) throw new ForbiddenException();
        List<Playlist> playlist = iPlaylistDAO.getPlaylists();

        PlaylistsDTO playlistsDTO = new PlaylistsDTO();
        playlistsDTO.playlists = new ArrayList<>();

        int totalLength = 0;
        for(Playlist p : playlist){
            PlaylistDTO playlistDTO = new PlaylistDTO();
            playlistDTO.id = p.getId();
            playlistDTO.name = p.getName();
            if(p.getOwner().equals(username)) playlistDTO.owner = true;
            else playlistDTO.owner = false;
            playlistsDTO.playlists.add(playlistDTO);
            totalLength += p.getLength();
        }
        playlistsDTO.length = totalLength;
        return playlistsDTO;
    }

    @Inject
    public void setPlaylistDAO(IPlaylistDAO iPlaylistDAO){
        this.iPlaylistDAO = iPlaylistDAO;
    }

    @Inject
    public void setTokenDAO(ITokenDAO iTokenDAO){
        this.iTokenDAO = iTokenDAO;
    }
}
