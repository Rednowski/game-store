package pl.edu.wszib.game.store.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.wszib.game.store.dao.impl.memory.GameRepository;

@Controller
public class CommonController {

    private final GameRepository gameRepository;

    public CommonController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        gameRepository.getAll();
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
