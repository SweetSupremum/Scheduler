package com.Scheduled.Scheduled_server.scheduled;


import com.Scheduled.Scheduled_server.service.GameServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final GameServiceImpl gameService;

    // cron = "@hourly"
    @Scheduled(fixedRateString = "${fixedRate}")
    public void reportCurrentTime() throws IOException {
        gameService.loadGames(new Date());
        System.err.println(gameService.getAll().size());
    }

}
