package com.Scheduled.Scheduled_server.service;

import com.Scheduled.Scheduled_server.helper.SchedulerHelper;
import com.Scheduled.Scheduled_server.model.Game;
import com.Scheduled.Scheduled_server.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.paginationStart;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.paginationStep;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectorFooterLi;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.selectorGames;
import static com.Scheduled.Scheduled_server.constants.SchedulerServiceConstants.startUrl;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final GameRepository gameRepository;

    public void parser() throws IOException {
        IntStream
                .range(paginationStart,
                        SchedulerHelper
                                .pagesCount(
                                        Jsoup
                                                .connect(startUrl + paginationStart).get()
                                                .select(selectorFooterLi)
                                                .stream()
                                                .map(Element::text).collect(Collectors.toList()))
                )
                .map(current -> current * paginationStep)
                .boxed().map(current -> startUrl + current)
                .collect(Collectors.toList())
                .parallelStream().forEach(url -> {
            try {
                List<Game> games = Jsoup.connect(url).get()
                        .select(selectorGames).stream().map(SchedulerHelper::parseGame)
                        .distinct().collect(Collectors.toList());
                gameRepository.saveAll(games);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        System.out.println(gameRepository.findAll().size());
    }
}
