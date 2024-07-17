package pl.edu.wszib.game.store.services.impl;

import org.springframework.stereotype.Service;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.services.IGameService;

import java.util.List;
import java.util.Optional;
@Service
public class GameService implements IGameService {

    private final IGameDAO gameDAO;

    public GameService(IGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Override
    public void save(Game game) {
        this.gameDAO.save(game);
    }

    @Override
    public Optional<Game> getById(int id) {
        return this.gameDAO.getById(id);
    }

    @Override
    public void update(Game game, int id, String fileName) {
        game.setId(id);
        game.setPicture(fileName);
        this.gameDAO.update(game);
    }

    @Override
    public void updateKeepPicture(Game game, int id) {
        game.setId(id);
        this.gameDAO.updateKeepPicture(game);
    }

    @Override
    public List<Game> getAll() {
        return this.gameDAO.getAll();
    }

    @Override
    public List<Game> getByPattern(String pattern) {
        return this.gameDAO.getByPattern(pattern);
    }
}
