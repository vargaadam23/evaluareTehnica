package com.adam.evaluaretehnica.badge.http;

import com.adam.evaluaretehnica.badge.types.BadgeType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

public record BadgeCreationRequest(
        @NotEmpty
        @Size(min = 4)
        String title,
        @NotNull
        @Min(1)
        int importance,
        @NotNull
        @Size(min=4)
        String badgeDescription,
        @NotNull
        BadgeType badgeType,
        @RequestParam("cssStyles")
        HashMap<String,String> cssStyles,
        @NotNull
        @NotEmpty
        String conditionValue
) {
}
