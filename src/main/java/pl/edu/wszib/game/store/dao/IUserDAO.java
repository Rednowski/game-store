package pl.edu.wszib.game.store.dao;

import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {
    Optional<User> getById(int id);
    Optional<User> getByLogin(String login);
    List<User> getAll();
    void save(User user);
    void remove(int id);
    void update(User user);
    void addGameToUser(User user, int gameId);
}
