package com.adam.evaluaretehnica.userquest;

import com.adam.evaluaretehnica.badge.BadgeService;
import com.adam.evaluaretehnica.exception.UnauthorizedStatusChangeException;
import com.adam.evaluaretehnica.quest.http.UserQuestStatusChangeRequest;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuestService {
    private final UserQuestRepository userQuestRepository;
    private final BadgeService badgeService;
    private final Logger logger = LoggerFactory.getLogger(UserQuestService.class);
    private final UserService userService;

    public void createUserQuestsForQuest(List<UserQuest> userQuestList) {
        userQuestRepository.saveAll(userQuestList);
    }

    public List<UserQuest> getAllCurrentUserQuests() {
        return userQuestRepository.findByUserAndIsFinalised(
                userService.getCurrentUser(), false
        );
    }

    public void handleUserQuestStatusChange(UserQuestStatusChangeRequest statusChangeRequest) throws UnauthorizedStatusChangeException{
        UserQuest userQuest = userQuestRepository.findById(statusChangeRequest.userQuestId()).orElseThrow();

        //Ignore request if the user quest is already finalised
        if(userQuest.getQuest().isFinalised() || userQuest.isFinalised()){
            throw new UnauthorizedStatusChangeException(
                    "Status change unauthorized! Quest is already finalised");
        }

        User questUser = userQuest.getUser();
        User questMaster = userQuest.getQuest().getQuestMaster();
        User currentUser = userService.getCurrentUser();

        //Check if current user is quest master or quest user
        boolean isCurrentUserQuestMaster = currentUser.getId().equals(questMaster.getId());
        boolean isCurrentUserQuestUser = currentUser.getId().equals(questUser.getId());

        QuestStatus prevQuestStatus = userQuest.getQuestStatus();

       statusChangeRequest
                .questStatus()
                .handleStateChange(userQuest, isCurrentUserQuestMaster ,isCurrentUserQuestUser );
       logger.debug("User quest status changed from "+prevQuestStatus+" to "+userQuest.getQuestStatus()+" by user "+currentUser.getUsername());

       //If completed, check and add eligible badges to user
        if(statusChangeRequest.questStatus().equals(QuestStatus.COMPLETED)){
            badgeService.verifyAndAddBadgesToUser(userQuest.getUser());
        }

        userQuestRepository.save(userQuest);
    }

}
