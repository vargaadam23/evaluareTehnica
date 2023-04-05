package com.adam.evaluaretehnica.quest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestCreationRequest {
    private String name;
    private String description;
    private String shortDescription;
    private String expiresAt;
    private List<Long> users;
    private boolean requiresProof;
    private Integer prize;
}
