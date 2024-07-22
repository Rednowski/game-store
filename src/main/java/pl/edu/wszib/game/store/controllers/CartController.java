package pl.edu.wszib.game.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.services.ICartService;

@Controller
@RequestMapping(path = "/cart")
public class CartController {

    private final IGameDAO gameDAO;

    private final ICartService cartService;

    @Autowired
    HttpSession httpSession;

    public CartController(IGameDAO gameDAO, ICartService cartService) {
        this.gameDAO = gameDAO;
        this.cartService = cartService;
    }

    @GetMapping(path = "/add/{id}")
    public String addToCart(@PathVariable final int id) {
        this.cartService.addGameToCart(id);
        return "redirect:/";
    }

    @GetMapping
    public String cart(Model model) {
        model.addAttribute("cartSum", this.cartService.calculateTotalSum());
        return "cart";
    }
}
