package com.Scheduled.Scheduled_server.utils;

import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class GameServiceHelper {
    public Pair<List<Game>, List<Game>> rebootLists(Pair<List<Game>, List<String>> gamesAndInvalidIds, List<Game> oldGames) {

        return Pair.of(throwGamesList(oldGames, gamesAndInvalidIds), leaveGamesLists(oldGames, gamesAndInvalidIds.getFirst()));
    }

    private boolean isThrow(List<Game> newGames, List<String> invalidIds, Game oldGame) {
        return !(containsInNewGames(newGames, oldGame) || containsInInvalidIds(oldGame, invalidIds));
    }

    private boolean containsInNewGames(List<Game> newGames, Game game) {
        return newGames.contains(game);
    }

    private boolean containsInInvalidIds(Game game, List<String> invalidIds) {
        return invalidIds.contains(game.getId());
    }

    private List<Game> leaveGamesLists(List<Game> oldGames, List<Game> newGames) {
        return newGames
                .stream()
                .filter(game -> noContainsOldGames(oldGames, game))
                .collect(Collectors.toList());
    }

    private List<Game> throwGamesList(List<Game> oldGames, Pair<List<Game>, List<String>> gamesAndInvalidIds) {
        return oldGames
                .stream()
                .filter(game -> isThrow(gamesAndInvalidIds.getFirst(), gamesAndInvalidIds.getSecond(), game))
                .collect(Collectors.toList());
    }

    private boolean noContainsOldGames(List<Game> oldGames, Game game) {
        return !oldGames.contains(game);
    }


}
