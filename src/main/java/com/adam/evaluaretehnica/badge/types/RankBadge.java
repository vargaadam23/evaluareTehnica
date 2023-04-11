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
public class RankBadge extends Badge {
    private int requiredRank;

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.RANK;
    }

    @Override
    public Badge setValue(String value) {
        this.requiredRank = Integer.parseInt(value);
        return this;
    }

    @Override
    public boolean isEligibleForUser(User user) {
        return user.getRank() >= requiredRank;
    }
}
