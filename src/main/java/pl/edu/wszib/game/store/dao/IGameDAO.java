package pl.edu.wszib.game.store.dao;

import pl.edu.wszib.game.store.model.Game;

import java.util.List;
import java.util.Optional;

public interface IGameDAO {
    Optional<Game> getById(int id);
    List<Game> getAll();
    List<Game> getByPattern(String pattern);
    void save(Game game);
    void remove(int id);
    void update(Game game);
}
