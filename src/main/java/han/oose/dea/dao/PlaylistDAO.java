package han.oose.dea.dao;

import han.oose.dea.domain.Playlist;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Default
public class PlaylistDAO implements IPlaylistDAO{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public List<Playlist> getPlaylists(){
            String sql = "SELECT DISTINCT p.id AS id, p.name AS name, p.owner AS owner, (SELECT SUM(duration) FROM tracks t " +
                    "INNER JOIN playlist_tracks pt ON t.id = pt.trackID WHERE p.id = pt.playlistID ) AS duration " +
                    "FROM playlist AS p " +
                    "LEFT OUTER JOIN playlist_tracks AS pt " +
                    "ON p.id = pt.playlistID";
            try (Connection connection = dataSource.getConnection()){
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery();

                List<Playlist> playlists = new ArrayList<>();

                while (rs.next()){
                    Playlist playlist = new Playlist();
                    playlist.setId(rs.getInt("id"));
                    playlist.setName(rs.getString("name"));
                    playlist.setOwner(rs.getString("owner"));
                    playlist.setLength(rs.getInt("duration"));

                    playlists.add(playlist);
                }

                return playlists;
            } catch (SQLException exception){
                exception.printStackTrace();
            }
            return null;
    }

    @Override
    public void deletePlaylist(int id){
        String sql = "DELETE FROM playlist WHERE id = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void addPlaylist(String name, String owner){
        String sql = "INSERT INTO playlist (name, owner) VALUES (?, ?)";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, owner);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void updatePlaylist(String name, int id){
        String sql = "UPDATE playlist SET name = ? WHERE id = ?";
        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
    }
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
