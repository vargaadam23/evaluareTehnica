package com.adam.evaluaretehnica.util;

import com.adam.evaluaretehnica.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SchedulerComponent {

    @Autowired
    private final UserService userService;

    @Scheduled(cron = "${adam.evaluareTehinica.rankingCron}")
    public void updateRanking(){
        System.out.println("Updated ranking!");
        userService.updateUserRanks();
    }
}
