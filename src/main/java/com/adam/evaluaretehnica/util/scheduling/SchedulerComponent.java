package com.adam.evaluaretehnica.util.scheduling;

import com.adam.evaluaretehnica.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

    private boolean jobPaused = false;
    private LocalDateTime lastRequestTime = LocalDateTime.now();

    @Scheduled(cron = "${adam.evaluareTehinica.rankingCron}")
    public void updateRanking() {
        if (jobPaused) {
            return;
        }
        System.out.println("Updated ranking!");
        userService.updateUserRanks();
    }

    //Added to minimize resource usage on hosting platforms
    @Scheduled(fixedRate = 60000)
    public void pauseJobsIfNoRequests() {
        LocalDateTime currentTime = LocalDateTime.now();
        Duration timeSinceLastRequest = Duration.between(lastRequestTime, currentTime);

        if (timeSinceLastRequest.toMinutes() >= 1) {
            System.out.println("Jobs paused");
            jobPaused = true;
        }else{
            jobPaused = false;
        }
    }
}
