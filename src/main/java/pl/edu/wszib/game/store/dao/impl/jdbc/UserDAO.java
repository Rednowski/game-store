package pl.edu.wszib.game.store.dao.impl.jdbc;

import org.springframework.stereotype.Repository;
import pl.edu.wszib.game.store.dao.IUserDAO;
import pl.edu.wszib.game.store.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAO implements IUserDAO {




    private final Connection connection;

    public UserDAO(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Optional<User> getById(int id) {
        String sql = "SELECT * FROM tuser WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
                user.setPassword(rs.getString("password"));
                user.setLogin(rs.getString("login"));
                user.getName(rs.getString("name"));
                user.getSurname(rs.getString("surname"));
                return Optional.empty();
            }
        } catch (SQLException e) {
             System.out.println("User entity error !!");
        }
         return Optional.empty();

    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void addGameToUser(User user, int gameId) {

    }
}
