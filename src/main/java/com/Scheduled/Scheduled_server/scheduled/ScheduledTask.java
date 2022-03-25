package com.Scheduled.Scheduled_server.scheduled;


import com.Scheduled.Scheduled_server.service.SchedulerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final SchedulerServiceImpl schedulerService;
    @Scheduled(fixedRateString = "${fixedRate}")
    public void reportCurrentTime() throws IOException {
        schedulerService.parser();
    }
}
