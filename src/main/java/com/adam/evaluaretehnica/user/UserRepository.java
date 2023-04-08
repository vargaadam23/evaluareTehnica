package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.user.http.RankedEntityResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    List<User> findByIdIn(Collection<Long> userIds);

    List<RankedEntityResponse> findRankedEntityByOrderByCurrencyTokensDesc();

    List<User> findByOrderByCurrencyTokensDesc();
}
