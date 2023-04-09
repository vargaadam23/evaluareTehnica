package com.adam.evaluaretehnica.user;

public interface RankedEntity {
    String getUsername();

    int getCurrencyTokens();

    int getRank();

    void setRank(int rank);

    void setCurrencyTokens(int amount);

    void setUsername(String username);

}
