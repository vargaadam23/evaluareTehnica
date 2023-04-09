package com.adam.evaluaretehnica.badge.types;

import com.adam.evaluaretehnica.badge.Badge;

public enum BadgeType {
    RANK {
        @Override
        public Badge createBadge() {
            return new RankBadge();
        }
    },
    CURRENCY_TOKEN {
        @Override
        public Badge createBadge() {
            return new CurrencyTokenBadge();
        }
    };

    public abstract Badge createBadge();
}
