package com.adam.evaluaretehnica.quest.http;

import com.adam.evaluaretehnica.http.ResponsePayload;
import com.adam.evaluaretehnica.userquest.UserQuest;

import java.util.List;


public record UserQuestResponse(
        List<UserQuest> userQuests) implements ResponsePayload {
}
