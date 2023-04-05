package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.userquest.UserQuest;
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
    @OneToMany(mappedBy = "quest")
    private List<UserQuest> assignedUserQuests;
    @ManyToOne
    @JoinColumn(name = "qm_id")
    private User questMaster;
    private boolean isFinalised;
    private int questMasterReward;
    private boolean requiresProof;
}
