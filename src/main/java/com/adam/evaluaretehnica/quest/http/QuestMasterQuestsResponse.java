package com.adam.evaluaretehnica.quest.http;

import com.adam.evaluaretehnica.http.ResponsePayload;
import com.adam.evaluaretehnica.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public record QuestMasterQuestsResponse (
    List<Quest> questMasterQuests) implements ResponsePayload {}
