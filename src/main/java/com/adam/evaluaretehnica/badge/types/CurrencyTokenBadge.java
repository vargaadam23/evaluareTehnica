package com.adam.evaluaretehnica.badge.types;

import com.adam.evaluaretehnica.badge.Badge;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyTokenBadge extends Badge {
    private int requiredTokenAmount;
    private int addedCondition;

}
