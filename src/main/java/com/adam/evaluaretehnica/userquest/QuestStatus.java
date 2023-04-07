package com.adam.evaluaretehnica.userquest;

public enum QuestStatus {
    CANCELLED {
        @Override
        public boolean handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest users can change the status to cancelled
            if(!isQuestUser){
                return false;
            }
            userQuest.setQuestStatus(CANCELLED);
            userQuest.setFinalised(true);
            userQuest.getQuest().calculateIndividualTokenPrize();
            return true;
        }
    },
    IN_REVIEW {
        @Override
        public boolean handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest users can change the status to in_review
            if(!isQuestUser){
                return false;
            }

            userQuest.setQuestStatus(IN_REVIEW);
            return true;
        }
    },
    FAILED {
        @Override
        public boolean handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest masters can change the status to failed
            if(!isQuestMaster){
                return false;
            }
            userQuest.setQuestStatus(FAILED);
            userQuest.setFinalised(true);
            return true;
        }
    },
    COMPLETED {
        @Override
        public boolean handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //Only quest masters can change the status to completed
            if(!isQuestMaster){
                return false;
            }
            userQuest.setQuestStatus(COMPLETED);
            //Add individual token prize to the balance of the user
            userQuest.getUser().addCurrencyTokensToBalance(
                    userQuest.getQuest().getIndividualTokenPrize());
            userQuest.setFinalised(true);
            return true;
        }
    },
    NEW {
        @Override
        public boolean handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser) {
            //No one can change the status back to null
            return false;
        }
    };

    public abstract boolean handleStateChange(UserQuest userQuest, boolean isQuestMaster, boolean isQuestUser);
}
