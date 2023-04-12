package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.badge.BadgeService;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.exception.QuestCreationException;
import com.adam.evaluaretehnica.quest.http.QuestCreationRequest;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserRepository;
import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.userquest.QuestStatus;
import com.adam.evaluaretehnica.userquest.UserQuest;
import com.adam.evaluaretehnica.util.ApplicationProperties;
import com.adam.evaluaretehnica.util.scheduling.QuestExpireTask;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestService {
    private final QuestRepository questRepository;
    private final BadgeService badgeService;
    private final Logger logger = LoggerFactory.getLogger(QuestService.class);
    private final UserService userService;
    private final TaskScheduler taskScheduler;
    private final ApplicationProperties applicationProperties;

    public Quest createQuestWithCreationRequest(QuestCreationRequest request) throws NotEnoughTokensException, QuestCreationException {
        //Get users from request and current user
        User currentUser = userService.getCurrentUser();
        if(request.users().contains(currentUser.getId())){
            throw new QuestCreationException("Quest master can not be assigned to quest as user!");
        }

        LocalDateTime expiresAt = LocalDateTime.parse(request.expiresAt());

        if(Duration.between(LocalDateTime.now(),expiresAt).toMinutes() < Integer.parseInt(
                applicationProperties.getConfigValue("maxExpirationDuration"))){
            throw new QuestCreationException("Quest expiration needs to be further than 2 minutes!");
        }

        //Create quest with already known properties
        Quest quest = Quest.builder()
                .name(request.name())
                .description(request.description())
                .shortDescription(request.shortDescription())
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .isFinalised(false)
                .assignedUserQuests(new ArrayList<>())
                .requiresProof(request.requiresProof())
                .totalTokenPrize(request.prize())
                .build();

        UserQuest userQuest = null;


        List<User> userList = userService.getUsersBasedOnIdList(request.users());

        if(userList.isEmpty()){
            throw new QuestCreationException("Provided users were not found while creating quest!");
        }

        //Subtract the prize amount from the quest master's balance
        currentUser.subtractCurrencyTokensFromBalance(request.prize());

        logger.debug("Subtracted "+request.prize()+" tokens for quest creation from user "+currentUser.getUsername());

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
        logger.info("Saved quest with id "+quest.getId());

        //Schedule the expiration date task
        taskScheduler.schedule(
                new QuestExpireTask(quest, questRepository,badgeService,
                        applicationProperties.getConfigValue("questMasterReward")),
                Date.from(quest.getExpiresAt().atZone(ZoneId.systemDefault()).toInstant())
        );

        return quest;
    }

    public List<Quest> getQuestMasterQuests() {
        return questRepository.findByQuestMasterAndIsFinalised(
                userService.getCurrentUser(), false
        );
    }
}
