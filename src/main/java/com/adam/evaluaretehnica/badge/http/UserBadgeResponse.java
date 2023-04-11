package com.adam.evaluaretehnica.badge.http;

import com.adam.evaluaretehnica.http.ResponsePayload;

import java.util.List;

public record UserBadgeResponse(List<BadgeResponse> badges) implements ResponsePayload {

}
