package com.adam.evaluaretehnica.util.scheduling;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        schedulerComponent.setLastRequestTime(LocalDateTime.now());
        schedulerComponent.setJobPaused(false);
        System.out.println("Jobs restarted!");
        filterChain.doFilter(request,response);
    }
}
