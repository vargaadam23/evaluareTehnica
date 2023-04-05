package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.userquest.UserQuest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quests")
public class QuestController {
    @GetMapping("/user-quests")
    public List<UserQuest> getAllQuestsForUser() {
        return null;
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
        return null;
    }

    @PostMapping("/create")
    public void createNewQuest(@RequestBody QuestCreationRequest creationRequest) {
        //create a new quest
    }
}
