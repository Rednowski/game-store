package pl.edu.wszib.game.store.services;

import pl.edu.wszib.game.store.model.Game;

import java.util.List;
import java.util.Optional;

public interface IGameService {
    void save(Game game);
    Optional<Game> getById(int id);
    void update(Game game, int id, String fileName);
    void updateKeepPicture(Game game, int id);
    List<Game> getAll();
    List<Game> getByPattern(String pattern);
}
