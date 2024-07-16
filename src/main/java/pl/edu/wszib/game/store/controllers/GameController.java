package pl.edu.wszib.game.store.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.exceptions.GameValidationException;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.image.handling.FileUpload;
import pl.edu.wszib.game.store.model.User;
import pl.edu.wszib.game.store.validators.GameValidator;

import java.io.IOException;
import java.util.Optional;

@Controller
public class GameController {

    private final IGameDAO gameDAO;

    private final HttpSession httpSession;

    public static String uploadDir = "src/main/resources/static/game-logos";

    public GameController(IGameDAO gameDAO, HttpSession httpSession) {
        this.gameDAO = gameDAO;
        this.httpSession = httpSession;
    }

    @RequestMapping(path = "/game/add", method = RequestMethod.GET)
    public String addForm(Model model) {
        if(((User) this.httpSession.getAttribute("user")).getRole() != User.Role.ADMIN) {
            return "redirect:/";
        }
        model.addAttribute("gameModel", new Game());
        return "gameForm";
    }

    @PostMapping(path = "/game/add")
    public String saveGame(Game game, @RequestParam("image")
                                 MultipartFile image) throws IOException {
        if(((User) this.httpSession.getAttribute("user")).getRole() != User.Role.ADMIN) {
            return "redirect:/";
        }
        try {
            GameValidator.validateDescription(game.getDescription());
            GameValidator.validateTags(game.getTags());
            GameValidator.validatePrice(game.getPrice());
            GameValidator.validatePublisher(game.getPublisher());
            GameValidator.validateTitle(game.getTitle());
            GameValidator.validateImageSize(image.getSize());
        } catch (GameValidationException e) {
            e.printStackTrace();
           return "redirect:/game/add";
        }
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            FileUpload.saveFile(uploadDir, fileName, image);

            game.setPicture(fileName);
            this.gameDAO.save(game);
        } else {
            game.setPicture("placeholder.png");
            this.gameDAO.save(game);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/game/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable int id, Model model) {
        if(((User) this.httpSession.getAttribute("user")).getRole() != User.Role.ADMIN) {
            return "redirect:/";
        }
        Optional<Game> gameBox = this.gameDAO.getById(id);
        if(gameBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("gameModel", gameBox.get());
        }
        return "gameForm";
    }

    @PostMapping(path = "/game/edit/{id}")
    public String editGame(@ModelAttribute Game game,
                           @PathVariable int id,
                           @RequestParam("image") MultipartFile image) throws IOException {

        if(((User) this.httpSession.getAttribute("user")).getRole() != User.Role.ADMIN) {
            return "redirect:/";
        }
        try {
            GameValidator.validateDescription(game.getDescription());
            GameValidator.validateTags(game.getTags());
            GameValidator.validatePrice(game.getPrice());
            GameValidator.validatePublisher(game.getPublisher());
            GameValidator.validateTitle(game.getTitle());
            GameValidator.validateImageSize(image.getSize());
        } catch (GameValidationException e) {
            e.printStackTrace();
            return "redirect:/game/edit/" + id;
        }
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            FileUpload.saveFile(uploadDir, fileName, image);

            game.setPicture(fileName);
            game.setId(id);
            this.gameDAO.update(game);
        } else {
            game.setId(id);
            this.gameDAO.updateNoPicture(game);
        }

        return "redirect:/";
    }
}
