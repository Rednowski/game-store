package pl.edu.wszib.game.store.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.game.store.model.User;
import pl.edu.wszib.game.store.services.IGameService;
import pl.edu.wszib.game.store.session.SessionConstants;

import java.util.List;

@Controller
public class CommonController {

    private final IGameService gameService;
    private final HttpSession httpSession;

    public CommonController(IGameService gameService, HttpSession httpSession) {
        this.gameService = gameService;
        this.httpSession = httpSession;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required = false) String pattern) {
        if(pattern == null) {
            model.addAttribute("games", this.gameService.getAll());
            model.addAttribute("pattern","");
        } else {
            model.addAttribute("games", this.gameService.getByPattern(pattern));
            model.addAttribute("pattern",pattern);
        }
        return "index";
    }

    @RequestMapping(path = "about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }

    @RequestMapping(path = "library", method = RequestMethod.GET)
    public String library(Model model) {
        User user = (User) this.httpSession.getAttribute(SessionConstants.USER_KEY);
        if(user == null) {
            return "redirect:/login";
        }
        model.addAttribute("games",
                this.gameService.getAllUserGames(user.getOwnedGames().stream().toList()));
        return "library";
    }
}
