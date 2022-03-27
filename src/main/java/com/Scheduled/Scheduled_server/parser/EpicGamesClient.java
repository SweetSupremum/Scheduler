package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.Scheduled.Scheduled_server.utils.Constants.ATTRIBUTE_HREF;
import static com.Scheduled.Scheduled_server.utils.Constants.NO_VALID_PRICE_GAME;
import static com.Scheduled.Scheduled_server.utils.Constants.PAGINATION_START;
import static com.Scheduled.Scheduled_server.utils.Constants.PAGINATION_STEP;
import static com.Scheduled.Scheduled_server.utils.Constants.REGEX_PATTERN_RUSSIAN_VERSION;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_FOOTER_LI;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_GAMES;
import static com.Scheduled.Scheduled_server.utils.Constants.START_URL;

@Component
@RequiredArgsConstructor
public class EpicGamesClient {
    private final GameParser gameParser;

    public List<Game> loadGames() throws IOException {
        return IntStream
                .range(PAGINATION_START, pagesCount())
                .mapToObj(current -> START_URL + current * PAGINATION_STEP)
                .collect(Collectors.toList())
                .parallelStream()
                .flatMap(url -> {
                            try {
                                return Jsoup.connect(url).get()
                                        .select(SELECTOR_GAMES).parallelStream();

                            } catch (IOException e) {
                                e.printStackTrace();
                                return Stream.empty();
                            }
                        }
                )
                .filter(this::isRussianVersion)
                .map(gameParser::parseGame)
                .filter(this::isValidGame).collect(Collectors.toList());
    }


    private int pagesCount() throws IOException {
        return
                Jsoup
                        .connect(START_URL + PAGINATION_START).get()
                        .select(SELECTOR_FOOTER_LI)
                        .stream()
                        .map(Element::text)
                        .filter(e -> !e.equals(Strings.EMPTY))
                        .mapToInt(Integer::parseInt)
                        .max().orElseThrow(NoSuchElementException::new);


    }

    private boolean isRussianVersion(Element elementId) {
        return Pattern.compile(REGEX_PATTERN_RUSSIAN_VERSION)
                .matcher(elementId.attr(ATTRIBUTE_HREF)).find();
    }

    private boolean isValidGame(Game game) {
        return game.getGameBase().getPrice() != NO_VALID_PRICE_GAME;
    }
}
