package com.adam.evaluaretehnica.userquest;

import com.adam.evaluaretehnica.exception.UnauthorizedStatusChangeException;

public enum QuestStatus {
    CANCELLED {
        @Override
        public void handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest users can change the status to cancelled
            if(!isQuestUser){
                throw new UnauthorizedStatusChangeException(
                        "Status change unauthorized! Only the quests user can change  it's status");
            }
            userQuest.setQuestStatus(CANCELLED);
            userQuest.setFinalised(true);
            userQuest.getQuest().calculateIndividualTokenPrize();
        }
    },
    IN_REVIEW {
        @Override
        public void handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest users can change the status to in_review
            if(!isQuestUser){
                throw new UnauthorizedStatusChangeException(
                        "Status change unauthorized! Only the quests user can change  it's status");
            }

            userQuest.setQuestStatus(IN_REVIEW);
        }
    },
    FAILED {
        @Override
        public void handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest masters can change the status to failed
            if(!isQuestMaster){
                throw new UnauthorizedStatusChangeException(
                        "Status change unauthorized! Only the quest master can change  it's status");
            }

            if(!userQuest.getQuestStatus().equals(IN_REVIEW)){
                throw new UnauthorizedStatusChangeException(
                        "Status change unauthorized! Quest has to be in review before it can be finalised");
            }
            userQuest.setQuestStatus(FAILED);
            userQuest.setFinalised(true);
        }
    },
    COMPLETED {
        @Override
        public void handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest masters can change the status to completed
            if(!isQuestMaster){
                throw new UnauthorizedStatusChangeException(
                        "Status change unauthorized! Only the quest master can change  it's status");
            }

            if(!userQuest.getQuestStatus().equals(IN_REVIEW)){
                throw new UnauthorizedStatusChangeException(
                        "Status change unauthorized! Quest has to be in review before it can be finalised");
            }
            userQuest.setQuestStatus(COMPLETED);
            //Add individual token prize to the balance of the user
            userQuest.getUser().addCurrencyTokensToBalance(
                    userQuest.getQuest().getIndividualTokenPrize());
            userQuest.setFinalised(true);
        }
    },
    NEW {
        @Override
        public void handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //No one can change the status back to new
            throw new UnauthorizedStatusChangeException(
                    "Status change unauthorized! Please authenticate as a user that has status change privileges!");
        }
    };

    public abstract void handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) throws UnauthorizedStatusChangeException;
}
