package han.oose.dea.dao;

import han.oose.dea.domain.Track;

import java.util.List;

public interface ITrackDAO {
    List<Track> getTracks(int id, boolean addTrack);
    void addTrackToPlaylist(int playlistID, int trackID, boolean offlineAvailable);
    void removeTrackFromPlaylist(int playlistID, int trackID);
}
