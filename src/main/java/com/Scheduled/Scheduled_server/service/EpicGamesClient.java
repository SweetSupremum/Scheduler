package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.parser.GameParser;
import com.Scheduled.Scheduled_server.utils.EpicGamesClientHelper;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.springframework.data.util.Pair;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
public class EpicGamesClient {

    private final GameParser gameParser;

    private static int attempt = 0;

    @Retryable(maxAttempts = 10, value = NoSuchElementException.class, backoff = @Backoff(delay = 500, multiplier = 2))
    public Pair<List<Game>, List<String>> loadGames() throws IOException {
        int pagesCount = pagesCount();
        attempt = 0;
        System.out.println(pagesCount);
        List<Game> games = IntStream
                .range(PAGINATION_START, pagesCount)
                .mapToObj(current -> START_URL + current * PAGINATION_STEP)
                .collect(Collectors.toList())
                .parallelStream()
                .flatMap(url -> {
                            try {
                                Elements elements = Jsoup.connect(url).get().select(SELECTOR_GAMES);
                               /* elements.stream().map(gameParser::parseGame).distinct().forEach(System.out::println);*/
                                return elements.parallelStream();

                            } catch (IOException e) {

                                return Stream.empty();
                            }
                        }
                )
                .filter(EpicGamesClientHelper::isRussianVersion)
                .map(gameParser::parseGame)
                .distinct()
                .collect(Collectors.toList());
        System.out.println(games.size());
       /* games.forEach(System.out::println);*/
        return EpicGamesClientHelper.totalGameLists(games);
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
