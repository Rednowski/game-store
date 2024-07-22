package pl.edu.wszib.game.store.services.impl;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.dao.IOrderDAO;
import pl.edu.wszib.game.store.exceptions.EmptyCartException;
import pl.edu.wszib.game.store.exceptions.IncorrectPositionsException;
import pl.edu.wszib.game.store.exceptions.UserIsEmpty;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.model.Order;
import pl.edu.wszib.game.store.model.Position;
import pl.edu.wszib.game.store.model.User;
import pl.edu.wszib.game.store.services.IOrderService;
import pl.edu.wszib.game.store.session.SessionConstants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderService implements IOrderService {

    private HttpSession httpSession;
    private final IGameDAO gameDAO;

    private final IOrderDAO orderDAO;

    public OrderService(HttpSession httpSession, IGameDAO gameDAO, IOrderDAO orderDAO) {
        this.httpSession = httpSession;
        this.gameDAO = gameDAO;
        this.orderDAO = orderDAO;
    }

    @Override
    public void confirmOrder() {
        Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
        User user = (User) this.httpSession.getAttribute(SessionConstants.USER_KEY);
        if(cart == null || cart.isEmpty()) {
            throw new EmptyCartException();
        }

        if(user == null) {
            throw new UserIsEmpty();
        }

        List<Position> toRemove = cart.stream().filter(p -> {
            Optional<Game> gameBox = this.gameDAO.getById(p.getGame().getId());
            if(gameBox.isEmpty() || user.getOwnedGames().contains(gameBox.get().getId())) {
                return true;
            }
            return false;
        }).toList();

        if(!toRemove.isEmpty()) {
            toRemove.forEach(cart::remove);
            throw new IncorrectPositionsException();
        }

        Order order = new Order();
        order.setUser((User) this.httpSession.getAttribute(SessionConstants.USER_KEY));
        order.setDate(LocalDateTime.now());
        order.setStatus(Order.Status.NEW);
        order.getPositions().addAll(cart);
        order.setTotalSum(order.getPositions().stream().mapToDouble(p -> p.getGame().getPrice()).sum());
        order.getPositions().forEach(position -> {
            user.getOwnedGames().add(position.getGame().getId());
        });

        this.orderDAO.persist(order);
        cart.clear();

        System.out.println(this.httpSession.getAttribute(SessionConstants.USER_KEY));

    }
}
