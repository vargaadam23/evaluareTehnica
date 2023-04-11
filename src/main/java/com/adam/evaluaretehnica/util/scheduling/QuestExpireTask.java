package com.adam.evaluaretehnica.util.scheduling;

import com.adam.evaluaretehnica.badge.BadgeService;
import com.adam.evaluaretehnica.quest.Quest;
import com.adam.evaluaretehnica.quest.QuestRepository;
import com.adam.evaluaretehnica.userquest.QuestStatus;
import com.adam.evaluaretehnica.userquest.UserQuest;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
public class QuestExpireTask implements Runnable {
    private Quest quest;
    private final QuestRepository questRepository;
    private final BadgeService badgeService;
    private final Logger logger = LoggerFactory.getLogger(QuestExpireTask.class);
    private final String questMasterReward;

    @Override
    public void run() {
        quest.setFinalised(true);
        logger.info("Running expiration task for quest "+quest.getName());

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
                try{
                    QuestStatus.IN_REVIEW.handleStateChange(userQuest, false, true);
                    QuestStatus.FAILED.handleStateChange(userQuest, true, false);
                }catch (Exception e){
                    logger.error("Exception occurred in quest expiration task: "+e.getMessage());
                }

            }
        }

        if (isQuestMasterPrizeValid && isAtLeastOneSubmitted) {
            quest.getQuestMaster().addCurrencyTokensToBalance(quest.getTotalTokenPrize() + Integer.parseInt(questMasterReward));
            badgeService.verifyAndAddBadgesToUser(quest.getQuestMaster());
        }

        questRepository.save(quest);
    }
}
