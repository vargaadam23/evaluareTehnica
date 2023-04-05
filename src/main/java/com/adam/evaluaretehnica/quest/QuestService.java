package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.userquest.UserQuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuestService {
    @Autowired
    private final QuestRepository questRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final UserQuestService userQuestService;

    public void createQuestWithCreationRequest(QuestCreationRequest request){
        Quest quest = Quest.builder()
                .name(request.getName())
                .description(request.getDescription())
                .shortDescription(request.getShortDescription())
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.parse(request.getExpiresAt()))
                .isFinalised(false)
                .questMasterReward(0)
                .requiresProof(request.isRequiresProof()).build();
    }
}
