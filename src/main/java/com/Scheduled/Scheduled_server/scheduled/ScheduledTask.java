package com.Scheduled.Scheduled_server.scheduled;


import com.Scheduled.Scheduled_server.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final SchedulerService schedulerService;

    @Scheduled(fixedRate = 3600000)
    public void reportCurrentTime() throws IOException {
        schedulerService.parser();
    }
}
