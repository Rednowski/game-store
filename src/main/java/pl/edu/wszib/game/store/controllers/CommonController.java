package pl.edu.wszib.game.store.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CommonController {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(path = "about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }
}
