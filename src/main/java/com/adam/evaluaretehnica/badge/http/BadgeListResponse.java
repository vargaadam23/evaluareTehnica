package com.adam.evaluaretehnica.badge.http;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.http.ResponsePayload;

import java.util.List;

public record BadgeListResponse(
        List<Badge> badges
) implements ResponsePayload {
}
