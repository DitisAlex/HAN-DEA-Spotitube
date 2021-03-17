package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;

import java.util.List;

public interface IPlaylistDAO {
    List<Playlist>getPlaylists();
    void deletePlaylist(int id);
    void addPlaylist(String name, String owner);
    void updatePlaylist(String name, int id);
}
