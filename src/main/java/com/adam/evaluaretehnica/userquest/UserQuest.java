package com.adam.evaluaretehnica.userquest;

import com.adam.evaluaretehnica.quest.Quest;
import com.adam.evaluaretehnica.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserQuest {
    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quest_id")
    private Quest quest;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
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
