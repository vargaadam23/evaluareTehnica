package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.badge.Badge;

import java.util.List;

public interface RankedEntity {
    String getUsername();

    int getCurrencyTokens();

    int getRank();

    void setRank(int rank);

    void setCurrencyTokens(int amount);

    void setUsername(String username);

}
