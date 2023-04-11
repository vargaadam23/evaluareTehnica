package com.adam.evaluaretehnica.user.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse implements RankedEntityResponse {
    private String username;
    private int currencyTokens;
    private int rank;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public int getCurrencyTokens() {
        return currencyTokens;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public void setCurrencyTokens(int amount) {
        this.currencyTokens = amount;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }
}
