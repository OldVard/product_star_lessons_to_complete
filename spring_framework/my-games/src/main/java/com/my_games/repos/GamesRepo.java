package com.my_games.repos;

import java.util.HashSet;
import java.util.Set;

import com.my_games.models.Game;
import com.my_games.models.Status;
import java.util.UUID;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class GamesRepo {
    private static Set<Game> games = new HashSet<>();

    static {
        games.add(new Game(UUID.randomUUID().toString(), "Cyberpunk 2077", Set.of("рпг", "фэнтези", "приключения"), "150GB", "RPG", Status.ACQUIRED.getDescription()));
        games.add(new Game(UUID.randomUUID().toString(), "Baldur's Gate 3", Set.of("приключения", "стратегия", "ролевые"), "145GB", "RPG", Status.ACQUIRED.getDescription()));
        games.add(new Game(UUID.randomUUID().toString(), "The Last of Us Part I", Set.of("шутер", "экшен", "приключения"), "100GB", "Action", Status.NOT_ACQUIRED.getDescription()));
    }

    public static Set<Game> getGamesSortedByName() {
        return games.stream()
        .sorted(Comparator.comparing(Game::getTitle))
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public static Set<Game> getAllGames() {
        return games;
    }
}
