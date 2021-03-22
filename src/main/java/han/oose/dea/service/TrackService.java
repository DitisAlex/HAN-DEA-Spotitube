package han.oose.dea.service;

import han.oose.dea.dao.IPlaylistDAO;
import han.oose.dea.dao.ITokenDAO;
import han.oose.dea.dao.ITrackDAO;
import han.oose.dea.domain.Track;
import han.oose.dea.service.dto.TrackDTO;
import han.oose.dea.service.dto.TracksDTO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("")
public class TrackService {
    private ITrackDAO iTrackDAO;

    @GET
    @Path("/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracks(@QueryParam("forPlaylist") int id, @QueryParam("token") String token){
        return Response.status(200).entity(listToDTOConverter(id, true)).build();
    }

    @GET
    @Path("/playlists/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@PathParam("id") int id, @QueryParam("token") String token){
        return Response.status(200).entity(listToDTOConverter(id, false)).build();
    }

    @DELETE
    @Path("/playlists/{playlistID}/tracks/{trackID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@PathParam("playlistID") int playlistID, @PathParam("trackID") int trackID, @QueryParam("token") String token){
        iTrackDAO.removeTrackFromPlaylist(playlistID, trackID);
        return Response.status(200).entity(listToDTOConverter(playlistID, false)).build();
    }

    @POST
    @Path("/playlists/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(TrackDTO trackDTO, @PathParam("id") int id, @QueryParam("token") String token){
        iTrackDAO.addTrackToPlaylist(id, trackDTO.id, trackDTO.offlineAvailable);
        return Response.status(201).entity(listToDTOConverter(id, false)).build();
    }

    public TracksDTO listToDTOConverter(int playlistID, boolean addTrack){
        List<Track> track = iTrackDAO.getTracks(playlistID, addTrack);

        TracksDTO tracksDTO = new TracksDTO();
        tracksDTO.tracks = new ArrayList<>();

        for(Track t : track) {
            TrackDTO trackDTO = new TrackDTO();
            trackDTO.id = t.getId();
            trackDTO.title = t.getTitle();
            trackDTO.performer = t.getPerformer();
            trackDTO.duration = t.getDuration();
            trackDTO.album = t.getAlbum();
            trackDTO.playcount = t.getPlaycount();
            trackDTO.publicationDate = t.getPublicationDate();
            trackDTO.description = t.getDescription();
            trackDTO.offlineAvailable = t.getOfflineAvailable();
            tracksDTO.tracks.add(trackDTO);
        }
        return tracksDTO;
    }

    @Inject
    public void setTrackDAO(ITrackDAO iTrackDAO){
        this.iTrackDAO = iTrackDAO;
    }

}
