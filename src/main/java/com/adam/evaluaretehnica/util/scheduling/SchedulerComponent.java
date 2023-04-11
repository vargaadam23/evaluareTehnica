package com.adam.evaluaretehnica.util.scheduling;

import com.adam.evaluaretehnica.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Getter
@Setter
public class SchedulerComponent {

    @Autowired
    private final UserService userService;

    private final Logger logger = LoggerFactory.getLogger(SchedulerComponent.class);

    private boolean jobPaused = false;
    private LocalDateTime lastRequestTime = LocalDateTime.now();

    @Scheduled(cron = "${adam.evaluareTehnica.rankingCron:0 * * * * *}")
    public void updateRanking() {
        if (jobPaused) {
            return;
        }
        logger.debug("Updating ranks!");
        userService.updateUserRanks();
    }

    //Added to minimize resource usage on hosting platforms, checks requests every 5 minutes
    @Scheduled(fixedRate = 300000)
    public void pauseJobsIfNoRequests() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeSinceLastRequest = Duration.between(lastRequestTime, currentTime);

        if (timeSinceLastRequest.toMinutes() >= 5) {
            logger.debug("Jobs paused!");
            jobPaused = true;
        }else{
            jobPaused = false;
        }
    }
}
