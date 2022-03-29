package com.Scheduled.Scheduled_server.utils;

import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Element;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.NO_VALID_PRICE_GAME;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_RUSSIAN_VERSION;

@UtilityClass
public class EpicGamesClientHelper {

    public boolean isRussianVersion(Element elementId) {
        return Pattern.compile(REGEX_PATTERN_RUSSIAN_VERSION)
                .matcher(elementId.attr(ATTRIBUTE_HREF)).find();
    }

    public boolean isValidGame(Game game) {
        return game.getGameBase().getPrice() != NO_VALID_PRICE_GAME;
    }

    public boolean isInvalidGame(Game game) {
        return !isValidGame(game);
    }

    public Pair<List<Game>, List<String>> totalGameLists(Supplier<Stream<Game>> streamSupplier) {
        return Pair.of(streamSupplier.get()
                        .filter(EpicGamesClientHelper::isValidGame)
                        .collect(Collectors.toList()),
                streamSupplier.get()
                        .filter(EpicGamesClientHelper::isInvalidGame)
                        .map(Game::getId).collect(Collectors.toList()));
    }
}
