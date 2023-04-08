package com.adam.evaluaretehnica.user.http;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse {
    List<RankedEntityResponse> rank;
}
