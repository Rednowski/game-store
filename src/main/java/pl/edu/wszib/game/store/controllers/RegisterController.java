package pl.edu.wszib.game.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RegisterController {

    @RequestMapping(path="/register", method = RequestMethod.GET)
    public String register() {
        return "/register";
    }

    @RequestMapping(path="/register", method = RequestMethod.POST)
    public String register2() {
        //TODO dokonczyc kontroler, zrobić registerservice
        return "redirect:/register";
    }
}
