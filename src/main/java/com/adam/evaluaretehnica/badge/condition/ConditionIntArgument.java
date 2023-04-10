package com.adam.evaluaretehnica.badge.condition;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ConditionIntArgument implements ConditionArgument<Integer> {
    private Integer conditionValue;

    @Override
    public Integer getConditionValue() {
        return conditionValue;
    }

    @Override
    public void setConditionValue(Integer value) {
        conditionValue = value;
    }
}
