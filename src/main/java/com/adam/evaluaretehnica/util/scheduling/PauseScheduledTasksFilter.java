package com.adam.evaluaretehnica.util.scheduling;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PauseScheduledTasksFilter extends OncePerRequestFilter {
    @Autowired
    private final SchedulerComponent schedulerComponent;

    private final Logger logger = LoggerFactory.getLogger(PauseScheduledTasksFilter.class);

    //Used to restart jobs if a new request is sent to the API
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(schedulerComponent.isJobPaused()){
            schedulerComponent.setLastRequestTime(LocalDateTime.now());
            schedulerComponent.setJobPaused(false);
            logger.info("Jobs restarted!");
        }
        filterChain.doFilter(request,response);
    }
}
