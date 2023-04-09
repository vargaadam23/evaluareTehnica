package com.adam.evaluaretehnica.badge.types;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.badge.condition.BadgeConditionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BadgeRepository extends JpaRepository<Badge, Long>, BadgeConditionRepository {
}
