package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.quest.http.QuestCreationRequest;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.userquest.QuestStatus;
import com.adam.evaluaretehnica.userquest.UserQuest;
import com.adam.evaluaretehnica.util.scheduling.QuestExpireTask;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {
    @Autowired
    private final QuestRepository questRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final TaskScheduler taskScheduler;

    @Transactional
    public void createQuestWithCreationRequest(QuestCreationRequest request) throws NotEnoughTokensException {
        //Create quest with already known properties
        Quest quest = Quest.builder()
                .name(request.name())
                .description(request.description())
                .shortDescription(request.shortDescription())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.parse(request.expiresAt()))
                .isFinalised(false)
                .assignedUserQuests(new ArrayList<>())
                .requiresProof(request.requiresProof())
                .totalTokenPrize(request.prize())
                .build();

        UserQuest userQuest = null;

        //Get users from request and current user
        User currentUser = userService.getCurrentUser();
        List<User> userList = userService.getUsersBasedOnIdList(request.users());

        //Remove quest master from list if present


        //Subtract the prize amount from the quest master's balance
        currentUser.subtractCurrencyTokensFromBalance(request.prize());

        quest.setQuestMaster(currentUser);

        //Create a UserQuest for each user
        for (User user : userList) {
            userQuest = UserQuest.builder()
                    .quest(quest)
                    .questStatus(QuestStatus.NEW)
                    .isFinalised(false)
                    .user(user)
                    .pathToProof(null)
                    .build();

            quest.addUserQuest(userQuest);
        }

        quest.calculateIndividualTokenPrize();

        questRepository.save(quest);

        //Schedule the expiration date task
        taskScheduler.schedule(
                new QuestExpireTask(quest, questRepository),
                Date.from(quest.getExpiresAt().atZone(ZoneId.systemDefault()).toInstant())
        );

        System.out.println(Date.from(quest.getExpiresAt().atZone(ZoneId.systemDefault()).toInstant()).toString());
    }

    public List<Quest> getQuestMasterQuests() {
        return questRepository.findByQuestMasterAndIsFinalised(
                userService.getCurrentUser(), false
        );
    }
}
