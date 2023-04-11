package com.adam.evaluaretehnica.quest.http;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record QuestCreationRequest (
        @NotEmpty
        @Size(min = 5)
        String name,
        @NotEmpty
        @Size(min = 5)
        String description,
        String shortDescription,
        @NotEmpty
        @NotNull
        String expiresAt,
        @NotEmpty
        List<Long> users,
        boolean requiresProof,
        @Min(value = 0)
        Integer prize
){}
