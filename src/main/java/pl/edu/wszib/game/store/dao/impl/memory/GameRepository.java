package pl.edu.wszib.game.store.dao.impl.memory;

import org.springframework.stereotype.Repository;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.model.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class GameRepository implements IGameDAO {

    List<Game> games = new ArrayList<>();

    private final IdSequence idSequence;

    public GameRepository(IdSequence idSequence) {
        this.idSequence = idSequence;
        games.add(new Game(this.idSequence.getId(), "The Witcher 3", "CD Projekt", 59.99,
            "RPG, Open World, Fantasy", "An epic game about the witcher Geralt.", "witcher3.jpg"));
        games.add(new Game(this.idSequence.getId(), "Battlefield 4", "Electronic Arts", 29.99,
                "FPS, Multiplayer, Simulator, War", "An intense war shooter.", "bf4.jpg"));
        games.add(new Game(this.idSequence.getId(), "Elden Ring", "FromSoftware", 69.99,
                "Action RPG, Fantasy, Dark Fantasy, Soulslike", "A new game from the creators of Dark Souls.", "eldenring.jpg"));
        games.add(new Game(this.idSequence.getId(), "Cyberpunk 2077", "CD Projekt", 49.99,
                "RPG, FPS, Cyberpunk, Open World", "Future, technology, and conspiracy.", "cyberpunk.jpg"));    }

    @Override
    public Optional<Game> getById(final int id) {
        return this.games.stream().filter(game -> game.getId() == id).findAny().map(this::copy);
    }

    @Override
    public List<Game> getAll() {
        return this.games.stream().map(this::copy).toList();
    }

    @Override
    public List<Game> getByPattern(String pattern) {
        return this.games.stream().filter(game -> game.getTitle()
                        .toLowerCase().contains(pattern.toLowerCase()) ||
                        game.getPublisher().toLowerCase()
                                .contains(pattern.toLowerCase()) ||
                game.getTagsList().stream().anyMatch(tag -> tag.toLowerCase()
                        .contains(pattern.toLowerCase())))
                .map(this::copy)
                .toList();
    }

    @Override
    public void save(Game game) {
        game.setId(this.idSequence.getId());
        this.games.add(game);
    }

    @Override
    public void remove(int id) {
        this.games.removeIf(game -> game.getId() == id);
    }

    @Override
    public void update(final Game game){
        this.games.stream().filter(g -> g.getId() == game.getId())
                .findAny().ifPresent(g -> {
            g.setTitle(game.getTitle());
            g.setTags(game.getTags());
            g.setPublisher(game.getPublisher());
            g.setPrice(game.getPrice());
            g.setDescription(game.getDescription());
            g.setPicture(game.getPicture());
        });
    }
    @Override
    public void updateKeepPicture(final Game game){
        this.games.stream().filter(g -> g.getId() == game.getId())
                .findAny().ifPresent(g -> {
                    g.setTitle(game.getTitle());
                    g.setTags(game.getTags());
                    g.setPublisher(game.getPublisher());
                    g.setPrice(game.getPrice());
                    g.setDescription(game.getDescription());
                });
    }

    private Game copy(Game game){
        Game copy = new Game();
        copy.setId(game.getId());
        copy.setTitle(game.getTitle());
        copy.setTags(game.getTags());
        copy.setPrice(game.getPrice());
        copy.setPublisher(game.getPublisher());
        copy.setDescription(game.getDescription());
        copy.setPicture(game.getPicture());
        return copy;
    }
}
