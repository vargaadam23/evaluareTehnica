package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestRepository extends JpaRepository<Quest, Long> {
    List<Quest> findByQuestMasterAndIsFinalised(User questMaster, boolean isFinalised);
}
