package com.adam.evaluaretehnica.util.scheduling;

import com.adam.evaluaretehnica.quest.Quest;
import com.adam.evaluaretehnica.quest.QuestRepository;
import com.adam.evaluaretehnica.userquest.QuestStatus;
import com.adam.evaluaretehnica.userquest.UserQuest;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QuestExpireTask implements Runnable {
    private Quest quest;
    private final QuestRepository questRepository;

    @Override
    public void run() {
        quest.setFinalised(true);
        System.out.println("Running task for "+quest.getName());

        //If the quest master didn't review all the quests before expiration or no quests have been submitted, the quest master gets no tokens
        boolean isQuestMasterPrizeValid = true;
        boolean isAtLeastOneSubmitted = false;
        for (UserQuest userQuest : quest.getAssignedUserQuests()) {
            if (userQuest.getQuestStatus().equals(QuestStatus.IN_REVIEW)) {
                isQuestMasterPrizeValid = false;
            }

            if(!userQuest.getQuestStatus().equals(QuestStatus.NEW) || !userQuest.getQuestStatus().equals(QuestStatus.CANCELLED)){
                isAtLeastOneSubmitted = true;
            }

            if (!userQuest.isFinalised()) {
                QuestStatus.FAILED.handleStateChange(userQuest, true, false);
            }
        }

        if (isQuestMasterPrizeValid && isAtLeastOneSubmitted) {
            quest.getQuestMaster().addCurrencyTokensToBalance(quest.getTotalTokenPrize() + 200);
        }

        questRepository.save(quest);
    }
}
