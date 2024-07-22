package pl.edu.wszib.game.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.wszib.game.store.exceptions.EmptyCartException;
import pl.edu.wszib.game.store.exceptions.IncorrectPositionsException;
import pl.edu.wszib.game.store.exceptions.NoGameFound;
import pl.edu.wszib.game.store.exceptions.UserIsEmpty;
import pl.edu.wszib.game.store.services.IOrderService;

@Controller
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public String order() {
        try {
            this.orderService.confirmOrder();
        } catch (EmptyCartException | IncorrectPositionsException | UserIsEmpty e) {
            return "redirect:/cart";
        }
        return "redirect:/";
    }
}
