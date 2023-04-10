package com.adam.evaluaretehnica.user.http;

import com.adam.evaluaretehnica.http.ResponsePayload;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingResponse implements ResponsePayload {
    List<RankedEntityResponse> rank;
}
