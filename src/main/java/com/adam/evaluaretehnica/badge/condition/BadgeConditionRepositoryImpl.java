package com.adam.evaluaretehnica.badge.condition;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class BadgeConditionRepositoryImpl implements BadgeConditionRepository{

    private final EntityManager entityManager;

    @Override
    public <T> List findByBadgeConditions(String condition, ConditionArgument<T> conditionArgument) {
        StringBuilder stringBuilder = new StringBuilder()
                .append("SELECT e FROM Badge e WHERE ")
                .append(condition);
        Query query = entityManager.createQuery(stringBuilder.toString());
        query.setParameter("value", conditionArgument.getConditionValue().toString());

        return query.getResultList();
    }
}
