package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.utils.EpicGamesClientHelper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.Scheduled.Scheduled_server.utils.Constants.PAGINATION_START;
import static com.Scheduled.Scheduled_server.utils.Constants.PAGINATION_STEP;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_FOOTER_LI;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_GAMES;
import static com.Scheduled.Scheduled_server.utils.Constants.START_URL;

@Component
@RequiredArgsConstructor
public class EpicGamesClient {
    private final GameParser gameParser;
    public List<Game> loadGames() throws IOException {
        List<Game> games = new Vector<>();
        IntStream
                .range(PAGINATION_START,
                        EpicGamesClientHelper
                                .pagesCount(
                                        Jsoup
                                                .connect(START_URL + PAGINATION_START).get()
                                                .select(SELECTOR_FOOTER_LI)
                                                .stream()
                                                .map(Element::text).collect(Collectors.toList()))
                )
                .map(current -> current * PAGINATION_STEP)
                .boxed().map(current -> START_URL + current)
                .collect(Collectors.toList())
                .parallelStream().forEach(url -> {

            try {
                games.addAll(Jsoup.connect(url).get()
                        .select(SELECTOR_GAMES).stream()
                        .filter(EpicGamesClientHelper::isRussianVersion)
                        .map(gameParser::parseGame)
                        .filter(EpicGamesClientHelper::isValidGame)
                        .distinct().collect(Collectors.toList()));
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
        return new ArrayList<>(games);
    }
}
