package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.exception.QuestCreationException;
import com.adam.evaluaretehnica.http.ETResponse;
import com.adam.evaluaretehnica.http.GenericResponse;
import com.adam.evaluaretehnica.quest.http.QuestCreationRequest;
import com.adam.evaluaretehnica.quest.http.QuestMasterQuestsResponse;
import com.adam.evaluaretehnica.quest.http.UserQuestResponse;
import com.adam.evaluaretehnica.quest.http.UserQuestStatusChangeRequest;
import com.adam.evaluaretehnica.userquest.UserQuestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quests")
public class QuestController {

    private final QuestService questService;
    private final UserQuestService userQuestService;

    @GetMapping("/user-quests")
    public ResponseEntity<ETResponse> getAllQuestsForUser() {
        UserQuestResponse userQuestResponse = new UserQuestResponse(userQuestService.getAllCurrentUserQuests());
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/quests/user-quests",
                        userQuestResponse)
        );
    }

    @PostMapping("/user-quests/status-change")
    public ResponseEntity<ETResponse> changeUserQuestStatus(@RequestBody @Valid UserQuestStatusChangeRequest userQuestStatusChangeRequest) {
        userQuestService.handleUserQuestStatusChange(userQuestStatusChangeRequest);
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/quests/user-quests/status-change",
                        new GenericResponse(
                                "Quest status successfully updated"
                        ))
        );
    }

    @GetMapping
    public ResponseEntity<ETResponse> getQuestMasterQuests() {
        QuestMasterQuestsResponse questMasterQuestsResponse = new QuestMasterQuestsResponse(questService.getQuestMasterQuests());
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/quests",
                        questMasterQuestsResponse)
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ETResponse> createNewQuest(@RequestBody @Valid QuestCreationRequest creationRequest) throws NotEnoughTokensException, QuestCreationException {
        questService.createQuestWithCreationRequest(creationRequest);
        return new ResponseEntity<>(
                new ETResponse(
                        HttpStatus.OK,
                        "/quests/create",
                        new GenericResponse(
                                "Successfully created new quest"
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
