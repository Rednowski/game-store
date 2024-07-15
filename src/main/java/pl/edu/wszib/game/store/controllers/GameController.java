package pl.edu.wszib.game.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.wszib.game.store.dao.IGameDAO;
import pl.edu.wszib.game.store.model.Game;
import pl.edu.wszib.game.store.image.handling.FileUpload;

import java.io.IOException;

@Controller
public class GameController {

    private final IGameDAO gameDAO;

    public GameController(IGameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @RequestMapping(path = "/game/add", method = RequestMethod.GET)
    public String addForm(Model model) {
        model.addAttribute("gameModel", new Game());
        return "gameForm";
    }

    @PostMapping(path = "/game/upload")
    public String saveGame(Game game, @RequestParam("image")
                                 MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            game.setPicture(fileName);

            String uploadDir = "src/main/resources/static/game-logos";
            FileUpload.saveFile(uploadDir, fileName, image);
            this.gameDAO.save(game);
        } else {
            game.setPicture("placeholder.png");
            this.gameDAO.save(game);
        }
        return "redirect:/";
    }
}
