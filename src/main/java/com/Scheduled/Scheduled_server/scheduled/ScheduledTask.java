package com.Scheduled.Scheduled_server.scheduled;


import com.Scheduled.Scheduled_server.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final GameService gameService;

    // cron = "@hourly"
    @Scheduled(fixedRateString = "${fixedRate}")
    public void reportCurrentTime() throws IOException {
        gameService.rebootGames(ZonedDateTime.now(ZoneId.of("Z")));
        /*System.err.println(gameService.getAll().size());*/
    }

}
