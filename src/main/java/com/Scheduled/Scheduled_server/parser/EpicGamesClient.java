package com.Scheduled.Scheduled_server.parser;

import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.utils.EpicGamesClientHelper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.Scheduled.Scheduled_server.utils.Constants.PAGINATION_START;
import static com.Scheduled.Scheduled_server.utils.Constants.PAGINATION_STEP;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_GAMES;
import static com.Scheduled.Scheduled_server.utils.Constants.SELECTOR_PAGES_COUNT;
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
                .filter(EpicGamesClientHelper::isRussianVersion)
                .map(gameParser::parseGame)
                .filter(EpicGamesClientHelper::isValidGame).distinct().collect(Collectors.toList());
    }


    private int pagesCount() throws IOException {
        return
                Jsoup
                        .connect(START_URL + PAGINATION_START).get()
                        .select(SELECTOR_PAGES_COUNT)
                        .stream()
                        .mapToInt(element -> Integer.parseInt(element.text())).findFirst()
                        .orElseThrow(NoSuchElementException::new);
    }

}
