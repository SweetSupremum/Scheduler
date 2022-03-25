package com.Scheduled.Scheduled_server.service.Impl;

import com.Scheduled.Scheduled_server.error.custom_exception.ParserException;
import com.Scheduled.Scheduled_server.helper.SchedulerHelper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.model.GameHistory;
import com.Scheduled.Scheduled_server.parser.GameParser;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.PAGINATION_START;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.PAGINATION_STEP;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECTOR_FOOTER_LI;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.SELECTOR_GAMES;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.START_URL;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl {
    private final GameServiceImpl gameService;

    public void parser() throws IOException {

        gameService.deleteAllGames();
        IntStream
                .range(PAGINATION_START,
                        SchedulerHelper
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
                List<Game> games = Jsoup.connect(url).get()
                        .select(SELECTOR_GAMES).stream()
                        .filter(SchedulerHelper::isRussianVersion)
                        .map(element -> new GameParser(element).parseGame())
                        .distinct().collect(Collectors.toList());
                gameService.saveAll(games.stream().filter(gameService::isUpdate).collect(Collectors.toList()),
                        games.stream().filter(gameService::isAdd).collect(Collectors.toList()), games, new Date());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println(gameService.gamesCount());
    }
}
