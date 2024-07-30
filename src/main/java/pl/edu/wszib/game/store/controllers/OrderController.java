package pl.edu.wszib.game.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.edu.wszib.game.store.exceptions.EmptyCartException;
import pl.edu.wszib.game.store.exceptions.IncorrectPositionsException;
import pl.edu.wszib.game.store.exceptions.UserIsEmpty;
import pl.edu.wszib.game.store.services.IOrderService;

@Controller
public class OrderController {

    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order/add")
    public String order() {
        try {
            this.orderService.confirmOrder();
        } catch (EmptyCartException | IncorrectPositionsException | UserIsEmpty e) {
            return "redirect:/cart";
        }
        return "redirect:/";
    }

    @GetMapping("/order")
    public String orders(Model model) {
        try {
            model.addAttribute("orders", this.orderService.getOrdersForCurrentUser());
        } catch (UserIsEmpty e) {
            return "redirect:/";
        }

        return "orders";
    }
}
