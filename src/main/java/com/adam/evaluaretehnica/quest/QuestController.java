package com.adam.evaluaretehnica.quest;

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

    @Autowired
    private final QuestService questService;

    @Autowired
    private final UserQuestService userQuestService;

    @GetMapping("/user-quests")
    public ResponseEntity<UserQuestResponse> getAllQuestsForUser() {
        UserQuestResponse userQuestResponse = new UserQuestResponse(userQuestService.getAllCurrentUserQuests());
        return ResponseEntity.ok(userQuestResponse);
    }

    @PostMapping("/user-quests/status-change")
    public ResponseEntity<String> changeUserQuestStatus(@RequestBody @Valid UserQuestStatusChangeRequest userQuestStatusChangeRequest) {
        userQuestService.handleUserQuestStatusChange(userQuestStatusChangeRequest);
        return new ResponseEntity<>("Updated quest status", HttpStatus.OK);
    }

//    TODO
//    @PostMapping("/user-quests/upload-proof")
//    public void uploadProofOfQuest(@RequestBody @Valid Quest quest) {
//        //upload a file
//    }

    @GetMapping
    public ResponseEntity<QuestMasterQuestsResponse> getQuestMasterQuests() {
        QuestMasterQuestsResponse questMasterQuestsResponse = new QuestMasterQuestsResponse(questService.getQuestMasterQuests());
        return ResponseEntity.ok(questMasterQuestsResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewQuest(@RequestBody @Valid QuestCreationRequest creationRequest) {
        questService.createQuestWithCreationRequest(creationRequest);
        return new ResponseEntity<>("Created new quest", HttpStatus.CREATED);
    }
}
