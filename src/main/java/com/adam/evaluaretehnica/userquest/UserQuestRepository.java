package com.adam.evaluaretehnica.userquest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserQuestRepository extends JpaRepository<UserQuest,Long> {
    @Query(value = """
      select t from UserQuest t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :userId and t.isFinalised <> false\s
      """)
    public Optional<UserQuest> findUserQuestsByUser(Long userId);
}
