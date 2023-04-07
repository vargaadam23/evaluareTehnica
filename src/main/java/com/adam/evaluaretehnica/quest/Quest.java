package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.userquest.QuestStatus;
import com.adam.evaluaretehnica.userquest.UserQuest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quest {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private String shortDescription;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private int individualTokenPrize;
    private int totalTokenPrize;
    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL)
    private List<UserQuest> assignedUserQuests;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "qm_id")
    private User questMaster;
    private boolean isFinalised;
    private int questMasterReward;
    private boolean requiresProof;

    public void addUserQuest(UserQuest userQuest) {
        assignedUserQuests.add(userQuest);
    }

    public void calculateIndividualTokenPrize() {
        if (!assignedUserQuests.isEmpty() && totalTokenPrize > 0) {
            long count = assignedUserQuests.stream().filter(userQuest -> !userQuest.getQuestStatus().equals(QuestStatus.CANCELLED)).count();
            setIndividualTokenPrize((int) (totalTokenPrize / count));
        }
    }
}
