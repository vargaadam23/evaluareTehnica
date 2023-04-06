package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.userquest.UserQuest;
import com.adam.evaluaretehnica.userquest.UserQuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/quests")
public class QuestController {

    @Autowired
    private final QuestService questService;

    @Autowired
    private final UserQuestService userQuestService;

    @GetMapping("/user-quests")
    public List<UserQuest> getAllQuestsForUser() {
        return userQuestService.getAllCurrentUserQuests();
    }

    @PostMapping("/user-quests/status")
    public void changeUserQuestStatus(@RequestParam("status") String status) {
        //change quest status
    }

    @PostMapping("/user-quests/upload-proof")
    public void uploadProofOfQuest(@RequestBody Quest quest) {
        //upload a file
    }

    @GetMapping
    public List<Quest> getQuestMasterQuests(){
        return questService.getQuestMasterQuests();
    }

    @PostMapping("/create")
    public void createNewQuest(@RequestBody QuestCreationRequest creationRequest) {
        questService.createQuestWithCreationRequest(creationRequest);
    }
}
