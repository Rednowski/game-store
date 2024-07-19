package pl.edu.wszib.game.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.model.Position;
import pl.edu.wszib.game.store.session.SessionConstants;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "/cart")
public class CartController {

    private final IGameDAO gameDAO;

    @Autowired
    HttpSession httpSession;

    public CartController(IGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @GetMapping(path = "/add/{id}")
    public String addToCart(@PathVariable final int id) {
        Optional<Game> gameBox = this.gameDAO.getById(id);

        gameBox.ifPresent(game -> {
            final Set<Position> cart = (Set<Position>) this.httpSession.getAttribute(SessionConstants.CART_KEY);
            Optional<Position> alreadyGamePosition = cart.stream().filter(p -> p.getGame().getId() == id).findFirst();

            if (alreadyGamePosition.isEmpty()) {
                cart.add(new Position(0, game));
            }
        });


        return "redirect:/";
    }
}
