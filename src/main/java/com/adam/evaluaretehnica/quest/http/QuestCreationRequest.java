package com.adam.evaluaretehnica.quest.http;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestCreationRequest {
    @NotEmpty
    @Size(min = 5)
    private String name;
    @NotEmpty
    @Size(min = 5)
    private String description;
    private String shortDescription;
    @NotEmpty
//    TODO
//    @Pattern(regexp = "^d{4}-\\d{2}-\\d{2}[Tt]\\d{2}:\\d{2}:\\d{2}$", message = "Date must be of format YYYY-MM-DDThh:mm")
    private String expiresAt;
    @NotEmpty
    private List<Long> users;
    private boolean requiresProof;
    @Min(value = 0)
    private Integer prize;
}
