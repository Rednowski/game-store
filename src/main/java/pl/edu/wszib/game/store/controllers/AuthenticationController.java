package pl.edu.wszib.game.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.edu.wszib.game.store.services.IAuthenticationService;

@Controller
public class AuthenticationController {

    private final IAuthenticationService authenticationService;


    @Autowired
    HttpSession httpSession;

    public AuthenticationController(IAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("loginInfo", this.authenticationService.getLoginInfo());
        this.httpSession.removeAttribute("loginInfo");
        return "/login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login2(@RequestParam String login, @RequestParam String password) {
        // TODO walidacja danych
        this.authenticationService.login(login, password);
        if(this.httpSession.getAttribute("user") != null){
            return "redirect:/";
        }
        return "redirect:/login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout() {
        this.authenticationService.logout();
        return "redirect:/";
    }


}
