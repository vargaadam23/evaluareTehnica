package com.adam.evaluaretehnica.badge.condition;

import com.adam.evaluaretehnica.badge.Badge;

import java.util.List;

public interface BadgeConditionRepository {
    List<Badge> findByBadgeConditions();
}
