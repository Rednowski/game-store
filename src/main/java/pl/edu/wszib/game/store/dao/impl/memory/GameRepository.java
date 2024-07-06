package pl.edu.wszib.game.store.dao.impl.memory;

import org.springframework.stereotype.Component;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.model.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GameRepository implements IGameDAO {

    List<Game> games = new ArrayList<>();

    private int lastId = 0;
    public GameRepository() {
        games.add(new Game(++lastId,"Wiedźmin 3", "CD Projekt", 59.99,
                List.of("RPG", "Open World", "Fantasy"), "Epicka gra o wiedźminie Geralcie."));
        games.add(new Game(++lastId,"Battlefield 4", "Electronic Arts", 29.99,
                List.of("FPS", "Multiplayer", "Simulator", "War"), "Intensywna strzelanka wojenna."));
        games.add(new Game(++lastId,"Elden Ring", "FromSoftware", 69.99,
                List.of("Action RPG", "Fantasy", "Dark Fantasy", "Soulslike"), "Nowa gra od twórców Dark Souls."));
        games.add(new Game(++lastId,"Cyberpunk 2077", "CD Projekt", 49.99,
                List.of("RPG", "FPS", "Cyberpunk", "Open World"), "Przyszłość, technologia i spisek."));
    }

    @Override
    public Optional<Game> getById(final int id) {
        return this.games.stream().filter(game -> game.getId() == id).findAny();
    }

    @Override
    public List<Game> getAll() {
        return this.games;
    }

    @Override
    public List<Game> getByPattern(String pattern) {
        return this.games.stream().filter(game -> game.getTitle().toLowerCase().contains(pattern.toLowerCase()) ||
                        game.getPublisher().toLowerCase().contains(pattern.toLowerCase()) ||
                game.getTags().stream().anyMatch(tag -> tag.toLowerCase().contains(pattern.toLowerCase())))
                .toList();
    }

    @Override
    public void save(Game game) {
        game.setId(++this.lastId);
        this.games.add(game);
    }

    @Override
    public void remove(int id) {
        this.games.removeIf(game -> game.getId() == id);
    }

    @Override
    public void update(Game game){

    }
}
