package com.adam.evaluaretehnica.badge.http;

import com.adam.evaluaretehnica.http.ETResponse;
import com.adam.evaluaretehnica.http.ResponsePayload;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public record BadgeResponse (
        String title,
        String description,
        @ResponseBody
        Map<String, String> cssStyles
) implements  ResponsePayload{
}
