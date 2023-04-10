package com.adam.evaluaretehnica.badge.condition;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.badge.types.CurrencyTokenBadge;
import com.adam.evaluaretehnica.badge.types.RankBadge;
import com.adam.evaluaretehnica.user.User;

public enum BadgeType {
    RANK {

        @Override
        public Badge createBadge() {
            return new RankBadge();
        }

        @Override
        public String getRawCondition() {
            return "rank<=?";
        }

        @Override
        public ConditionArgument<Integer> createCondition(User user) {
            return ConditionIntArgument
                    .builder()
                    .conditionValue(user.getRank())
                    .build();
        }
    },
    CURRENCY_TOKEN {
        @Override
        public Badge createBadge() {
            return new CurrencyTokenBadge();
        }

        @Override
        public String getRawCondition() {
            return "currency_tokens<=?";
        }

        @Override
        public ConditionArgument<Integer> createCondition(User user) {
            return ConditionIntArgument
                    .builder()
                    .conditionValue(user.getCurrencyTokens())
                    .build();
        }
    };

    public abstract Badge createBadge();

    public abstract <G> ConditionArgument<G> createCondition(User user);

    public abstract String getRawCondition();
}
