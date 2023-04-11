package com.adam.evaluaretehnica.badge.types;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.user.User;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyTokenBadge extends Badge {
    private int requiredTokenAmount;

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.CURRENCY_TOKEN;
    }

    @Override
    public Badge setValue(String value) {
        requiredTokenAmount = Integer.parseInt(value);
        return this;
    }

    @Override
    public boolean isEligibleForUser(User user) {
        return user.getCurrencyTokens() >= requiredTokenAmount;
    }
}
