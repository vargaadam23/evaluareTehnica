package com.adam.evaluaretehnica.userquest;

import com.adam.evaluaretehnica.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserQuestRepository extends JpaRepository<UserQuest, Long> {
    List<UserQuest> findByUserAndIsFinalised(User user, boolean isFinalised);

    Optional<UserQuest> findById(Long id);
}
