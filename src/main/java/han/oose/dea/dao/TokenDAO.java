package han.oose.dea.dao;

import han.oose.dea.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class TokenDAO implements ITokenDAO{
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public User addTokenToDB(String token, String username){
        String sql = "UPDATE users SET token = ? WHERE username = ?";

        try (Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public String verifyToken(String token){
        String sql = "SELECT * FROM users WHERE token = ?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, token);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                String username = rs.getString("username");
                return username;
            }
        } catch (SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
