package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.userquest.QuestStatus;
import com.adam.evaluaretehnica.userquest.UserQuest;
import com.adam.evaluaretehnica.userquest.UserQuestService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {
    @Autowired
    private final QuestRepository questRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserQuestService userQuestService;

    @Transactional
    public void createQuestWithCreationRequest(QuestCreationRequest request){
        //Create quest with already known properties
        Quest quest = Quest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .shortDescription(request.getShortDescription())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.parse(request.getExpiresAt()))
                .isFinalised(false)
                .questMasterReward(0)
                .assignedUserQuests(new ArrayList<>())
                .requiresProof(request.isRequiresProof()).build();

        UserQuest userQuest = null;

        //Get users from request and current user
        List<User> userList = userService.getUsersBasedOnIdList(request.getUsers());
        User currentUser = userService.getCurrentUser();

        quest.setQuestMaster(currentUser);
        //Set the individual quest reward to be the total reward divided by the number of users that have the quest
        quest.setIndividualTokenPrize(userList.size() > 0 ? request.getPrize() / userList.size() : 0);

        //Create a UserQuest for each user
        for (User user : userList){
            userQuest = UserQuest.builder()
                    .quest(quest)
                    .questStatus(QuestStatus.NEW)
                    .isFinalised(false)
                    .user(user)
                    .pathToProof(null)
                    .build();

            quest.addUserQuest(userQuest);
        }

        questRepository.save(quest);
    }

    public List<Quest> getQuestMasterQuests(){
        return questRepository.findByQuestMasterAndIsFinalised(
                userService.getCurrentUser(), false
        );
    }
}
