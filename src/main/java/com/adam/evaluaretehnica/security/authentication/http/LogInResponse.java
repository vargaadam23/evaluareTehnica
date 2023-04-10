package com.adam.evaluaretehnica.security.authentication.http;

import com.adam.evaluaretehnica.http.ResponsePayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public record LogInResponse (
        String token ) implements ResponsePayload {}
