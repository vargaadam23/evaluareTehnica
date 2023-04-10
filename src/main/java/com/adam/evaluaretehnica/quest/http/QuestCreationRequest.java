package com.adam.evaluaretehnica.quest.http;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
//    TODO
//    @Pattern(regexp = "^d{4}-\\d{2}-\\d{2}[Tt]\\d{2}:\\d{2}:\\d{2}$", message = "Date must be of format YYYY-MM-DDThh:mm")
        String expiresAt,
        @NotEmpty
        List<Long> users,
        boolean requiresProof,
        @Min(value = 0)
        Integer prize
){}
