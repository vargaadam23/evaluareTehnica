package com.adam.evaluaretehnica.badge.condition;

import com.adam.evaluaretehnica.badge.types.CurrencyTokenBadge;
import com.adam.evaluaretehnica.badge.types.RankBadge;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class BadgeCondition {
    private List<String> conditionList;

    @PostConstruct
    public void init(){
        registerBadgeConditions();
    }

    private void registerBadgeConditions(){
        conditionList.add(RankBadge.condition);
        conditionList.add(CurrencyTokenBadge.condition);
    }
}
