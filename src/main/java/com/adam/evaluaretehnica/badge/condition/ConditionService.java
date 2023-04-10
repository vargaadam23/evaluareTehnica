package com.adam.evaluaretehnica.badge.condition;

import com.adam.evaluaretehnica.badge.types.BadgeRepository;
import com.adam.evaluaretehnica.badge.types.CurrencyTokenBadge;
import com.adam.evaluaretehnica.badge.types.RankBadge;
import com.adam.evaluaretehnica.user.User;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConditionService {
    private final List<BadgeType> badgeTypes = new ArrayList<>();

    private final BadgeRepository badgeRepository;

    @PostConstruct
    public void init() {
        registerBadgeConditions();
    }

    //Add desired badge conditions to be checked
    private void registerBadgeConditions() {
        badgeTypes.add(BadgeType.RANK);
        badgeTypes.add(BadgeType.CURRENCY_TOKEN);
    }

    public void addNewBadges(User user){
        for (BadgeType badgeType : badgeTypes){
            ConditionArgument<Object> conditionArgument = badgeType.createCondition(user);

        }
    }
}
