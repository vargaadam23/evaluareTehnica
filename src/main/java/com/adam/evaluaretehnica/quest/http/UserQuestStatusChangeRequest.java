package com.adam.evaluaretehnica.quest.http;

import com.adam.evaluaretehnica.userquest.QuestStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestStatusChangeRequest {
    @NotNull
    @Min(value = 1)
    private Long userQuestId;
    @NotNull
    private QuestStatus questStatus;
}
