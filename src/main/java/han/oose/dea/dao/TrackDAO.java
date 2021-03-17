package han.oose.dea.dao;

import han.oose.dea.domain.Track;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Default
public class TrackDAO implements ITrackDAO{

    @Resource(name = "jdbc/spotitube")
    private DataSource dataSource;

    @Override
    public List<Track> getTracks(int id, boolean addTrack){
        String sql = "SELECT * FROM tracks t " +
                "LEFT JOIN playlist_tracks pt " +
                "ON t.id = pt.trackID " +
                "WHERE pt.playlistID = ?";

        if (addTrack){
            sql = "SELECT DISTINCT * FROM tracks t " +
                    "LEFT JOIN playlist_tracks pt " +
                    "ON t.id = pt.trackID " +
                    "WHERE t.id NOT IN (SELECT trackID from playlist_tracks WHERE playlistID = ?) " +
                    "GROUP BY t.id";
        }

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            List<Track> tracks = new ArrayList<>();

            while(rs.next()){
                Track track= new Track();
                track.setId(rs.getInt("id"));
                track.setTitle(rs.getString("title"));
                track.setPerformer(rs.getString("performer"));
                track.setDuration(rs.getInt("duration"));
                track.setAlbum(rs.getString("album"));
                track.setPlaycount((rs.getInt("playcount")));
                track.setPublicationDate(rs.getString("publicationDate"));
                track.setDescription((rs.getString("description")));
                track.setOfflineAvailable((rs.getBoolean("offlineAvailable")));
                tracks.add(track);
            }
            return tracks;
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeTrackFromPlaylist(int playlistID, int trackID){
        String sql ="DELETE FROM playlist_tracks WHERE trackID = ? AND playlistID = ?";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, trackID);
            statement.setInt(2, playlistID);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void addTrackToPlaylist(int playlistID, int trackID, boolean offlineAvailable){
        String sql = "INSERT INTO playlist_tracks (playlistID, trackID, offlineAvailable) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, playlistID);
            statement.setInt(2, trackID);
            statement.setBoolean(3, offlineAvailable);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

}
