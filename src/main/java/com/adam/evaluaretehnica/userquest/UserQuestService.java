package com.adam.evaluaretehnica.userquest;

import com.adam.evaluaretehnica.quest.http.UserQuestStatusChangeRequest;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQuestService {
    @Autowired
    private final UserQuestRepository userQuestRepository;

    @Autowired
    private final UserService userService;

    public void createUserQuestsForQuest(List<UserQuest> userQuestList) {
        userQuestRepository.saveAll(userQuestList);
    }

    public List<UserQuest> getAllCurrentUserQuests() {
        return userQuestRepository.findByUserAndIsFinalised(
                userService.getCurrentUser(), false
        );
    }

    public boolean handleUserQuestStatusChange(UserQuestStatusChangeRequest statusChangeRequest) {
        UserQuest userQuest = userQuestRepository.findById(statusChangeRequest.getUserQuestId()).orElseThrow();

        if(userQuest.getQuest().isFinalised() || userQuest.isFinalised()){
            return false;
        }

        User questUser = userQuest.getUser();
        User questMaster = userQuest.getQuest().getQuestMaster();
        User currentUser = userService.getCurrentUser();

        //Check if current user is quest master or quest user
        boolean isCurrentUserQuestMaster = currentUser.getId().equals(questMaster.getId());
        boolean isCurrentUserQuestUser = currentUser.getId().equals(questUser.getId());

        boolean statusChangeResult = statusChangeRequest
                .getQuestStatus()
                .handleStateChange(userQuest, isCurrentUserQuestMaster ,isCurrentUserQuestUser );
        System.out.println("Status changing "+isCurrentUserQuestMaster+isCurrentUserQuestUser+" "+statusChangeRequest.getQuestStatus());
        if(statusChangeResult){
            userQuestRepository.save(userQuest);
        }

        return statusChangeResult;
    }

}
