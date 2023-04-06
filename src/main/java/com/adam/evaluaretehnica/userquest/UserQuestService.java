package com.adam.evaluaretehnica.userquest;

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

    public void createUserQuestsForQuest(List<UserQuest> userQuestList){
        userQuestRepository.saveAll(userQuestList);
    }

    public List<UserQuest> getAllCurrentUserQuests(){
        return userQuestRepository.findByUserAndIsFinalised(
                userService.getCurrentUser(), false
        );
    }

}
