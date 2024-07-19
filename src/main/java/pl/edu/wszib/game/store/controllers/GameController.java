package pl.edu.wszib.game.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.game.store.exceptions.GameValidationException;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.image.handling.FileUpload;
import pl.edu.wszib.game.store.services.IGameService;
import pl.edu.wszib.game.store.validators.GameValidator;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(path = "/game")
public class GameController {


    private final IGameService gameService;

    public static String uploadDir = "src/main/resources/static/game-logos";

    public GameController(IGameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String addForm(Model model) {
        model.addAttribute("gameModel", new Game());
        return "gameForm";
    }

    @PostMapping(path = "/add")
    public String saveGame(Game game, @RequestParam("image")
                                 MultipartFile image) throws IOException {
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
            this.gameService.save(game);
        } else {
            game.setPicture("placeholder.png");
            this.gameService.save(game);
        }
        return "redirect:/";
    }

    @RequestMapping(path = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable int id, Model model) {
        Optional<Game> gameBox = this.gameService.getById(id);
        if(gameBox.isEmpty()) {
            return "redirect:/";
        } else {
            model.addAttribute("gameModel", gameBox.get());
        }
        return "gameForm";
    }

    @PostMapping(path = "/edit/{id}")
    public String editGame(@ModelAttribute Game game,
                           @PathVariable int id,
                           @RequestParam("image") MultipartFile image) throws IOException {
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
            this.gameService.update(game, id, fileName);
        } else {
            this.gameService.updateKeepPicture(game, id);
        }

        return "redirect:/";
    }
}
