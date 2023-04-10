package com.adam.evaluaretehnica.badge.condition;

import com.adam.evaluaretehnica.badge.Badge;

import java.util.List;

public interface BadgeConditionRepository {
    <T> List<Badge> findByBadgeConditions(String condition, ConditionArgument<T> conditionArgument);
}
