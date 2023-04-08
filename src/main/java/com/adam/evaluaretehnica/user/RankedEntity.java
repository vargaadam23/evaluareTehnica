package com.adam.evaluaretehnica.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface RankedEntity {
    String getUsername();
    int getCurrencyTokens();
    int getRank();
    void setRank(int rank);
    void setCurrencyTokens(int amount);
    void setUsername(String username);

}
