package com.adam.evaluaretehnica.userquest;

import com.adam.evaluaretehnica.quest.Quest;
import com.adam.evaluaretehnica.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserQuest {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean isFinalised;
    @Enumerated
    private QuestStatus questStatus;
    private String pathToProof;

    public boolean getRequiresProof(){
        return quest.isRequiresProof();
    }

}
