package com.my_games.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.my_games.repos.GamesRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.my_games.models.Game;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/games")
public class GamesController {

    @GetMapping
    public String showGames(Model model) {
        model.addAttribute("games", GamesRepo.getGamesSortedByName());
        return "games-list";
    }

    @GetMapping("/create-form")
    public String showCreateForm() {
        return "create-form";
    }

    @PostMapping("/create")
    public String createGame(Game game) {
        game.setId(UUID.randomUUID().toString());
        GamesRepo.getAllGames().add(game);
        return "redirect:/games";
    }

    @GetMapping("/edit-form/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        Game game = getGameById(id);
        model.addAttribute("game", game);
        return "edit-form";
    }

    @PostMapping("/update")
    public String updateBook(Game game) {
        Game oldGame = getGameById(game.getId());

        GamesRepo.getAllGames().remove(oldGame);
        GamesRepo.getAllGames().add(game);

        return "redirect:/games";
    }

    @GetMapping("/delete/{id}")
    public String deleteGameById(@PathVariable String id) {
        Game game = getGameById(id);

        GamesRepo.getAllGames().remove(game);
        return "redirect:/games";
    }

    // Вспомогательное
    private Game getGameById(String id) {
        return GamesRepo.getAllGames().stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Игра не найдена :("));
    }
}
