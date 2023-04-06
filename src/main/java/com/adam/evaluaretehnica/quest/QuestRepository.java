package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestRepository extends JpaRepository<Quest,Long> {
    List<Quest> findByQuestMasterAndIsFinalised(User questMaster, boolean isFinalised);
}
