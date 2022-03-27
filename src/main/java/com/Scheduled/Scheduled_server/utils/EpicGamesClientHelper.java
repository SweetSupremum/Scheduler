package com.Scheduled.Scheduled_server.utils;


import com.Scheduled.Scheduled_server.model.Game;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.NO_VALID_PRICE_GAME;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_RUSSIAN_VERSION;

@UtilityClass
public class EpicGamesClientHelper {

    public int pagesCount(List<String> pageList) {
        return Collections.max(Arrays.stream(pageList.toArray())
                .filter(e -> !e.toString().equals(Strings.EMPTY)).map(e -> Integer.parseInt(e.toString()))
                .collect(Collectors.toList()), Comparator.naturalOrder());
    }

    public boolean isRussianVersion(Element elementId) {
        return Pattern.compile(REGEX_PATTERN_RUSSIAN_VERSION)
                .matcher(elementId.attr(ATTRIBUTE_HREF)).find();
    }

    public boolean isValidGame(Game game) {
        return game.getGameBase().getPrice() != NO_VALID_PRICE_GAME;
    }
}
