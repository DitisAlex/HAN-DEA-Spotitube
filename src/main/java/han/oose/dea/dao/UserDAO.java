package han.oose.dea.dao;

import han.oose.dea.domain.User;

import javax.annotation.Resource;
import javax.enterprise.inject.Default;
import javax.sql.DataSource;
import java.sql.*;

@Default
public class UserDAO implements IUserDAO {
    @Resource(name = "jdbc/spotitube")
    DataSource dataSource;

    @Override
    public User checkAuthenticated(String username, String password) {
        String sql = "select * from users where username = ? AND password = ?";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setToken(rs.getString("token"));
                return user;
            }
            return null;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
