package pl.edu.wszib.game.store.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.game.store.services.IGameService;

@Controller
public class CommonController {

    private final IGameService gameService;

    public CommonController(IGameService gameService) {
        this.gameService = gameService;
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
    public String library() {
        return "library";
    }
}
