package pl.edu.wszib.game.store.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.model.Position;
import pl.edu.wszib.game.store.services.ICartService;
import pl.edu.wszib.game.store.session.SessionConstants;

import java.util.Optional;
import java.util.Set;

@Service
public class CartService implements ICartService {

    private HttpSession httpSession;

    private final IGameDAO gameDAO;
    public CartService(HttpSession httpSession, IGameDAO gameDAO) {
        this.httpSession = httpSession;
        this.gameDAO = gameDAO;
    }

    public double calculateTotalSum() {
        Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
        if(cart == null) {
            return 0.0;
        }
        return cart.stream().mapToDouble(p -> p.getGame().getPrice()).sum();
    }

    @Override
    public void addGameToCart(int id) {
        Optional<Game> gameBox = this.gameDAO.getById(id);

        gameBox.ifPresent(game -> {
            final Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
            Optional<Position> alreadyGamePosition = cart.stream().filter(p -> p.getGame().getId() == id).findFirst();

            if (alreadyGamePosition.isEmpty()) {
                cart.add(new Position(0, game));
            }
        });
    }
}
