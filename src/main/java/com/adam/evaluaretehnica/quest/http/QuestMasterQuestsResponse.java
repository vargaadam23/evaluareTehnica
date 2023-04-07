package com.adam.evaluaretehnica.quest.http;

import com.adam.evaluaretehnica.quest.Quest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestMasterQuestsResponse {
    private List<Quest> questMasterQuests;
}
