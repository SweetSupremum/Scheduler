package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.parser.GameParser;
import com.Scheduled.Scheduled_server.utils.EpicGamesClientHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.Scheduled.Scheduled_server.utils.Constants.*;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public class EpicGamesClient {

    private static int attempt = 0;
    private final GameParser gameParser;

    @Retryable(maxAttempts = 10, value = NoSuchElementException.class, backoff = @Backoff(delay = 500, multiplier = 2))
    public List<Game> loadGames() throws IOException {
        int pagesCount = pagesCount();
        log.info("count attempt retryable {}", attempt);
        List<Element> gamesElement = IntStream
                .range(PAGINATION_START, pagesCount)
                .mapToObj(current -> START_URL + current * PAGINATION_STEP)
                .collect(toList())
                .parallelStream()
                .flatMap(url -> {
                            try {
                                return Jsoup.connect(url).get().select(SELECTOR_GAMES).parallelStream();

                            } catch (IOException e) {
                                log.error(url + " this url no download");
                                return Stream.empty();
                            }
                        }
                ).collect(toList());
        log.warn("this log for all cases games");
        List<Game> games = gamesElement.stream().map(gameParser::parseGame).collect(toList());
        List<Game> withNullPrice = games.stream().filter(game -> game.getPrice() == null).collect(toList());
        withNullPrice.forEach(nullPrice -> log.info("this game have null price -> {}", withNullPrice));
        List<Game> notIsRussianVersion = gamesElement.stream().filter(EpicGamesClientHelper::isNotRussianVersion)
                .map(gameParser::parseGame).collect(toList());
        notIsRussianVersion.forEach(game -> log.info("this game not is Russian version -> {}", game));
        log.warn("end log for all cases games");
        return gamesElement
                .stream()
                .filter(EpicGamesClientHelper::isRussianVersion)
                .map(gameParser::parseGame).distinct().collect(toList());
    }

    private int pagesCount() throws IOException {
        System.out.println(++attempt);
        return
                Jsoup
                        .connect(START_URL + PAGINATION_START).get()
                        .select(SELECTOR_PAGES_COUNT)
                        .stream()
                        .mapToInt(element -> Integer.parseInt(element.text())).distinct().findAny()
                        .orElseThrow(NoSuchElementException::new);
    }

}
