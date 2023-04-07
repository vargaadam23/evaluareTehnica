package com.adam.evaluaretehnica.quest.http;

import com.adam.evaluaretehnica.userquest.UserQuest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserQuestResponse {
    private List<UserQuest> userQuests;
}
