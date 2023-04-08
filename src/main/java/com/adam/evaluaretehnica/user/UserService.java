package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.user.http.RankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public List<User> getUsersBasedOnIdList(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public RankingResponse getUserRanks(){
        return new RankingResponse(userRepository.findRankedEntityByOrderByCurrencyTokensDesc());
    }

    public void updateUserRanks(){
        List<User> users = userRepository.findByOrderByCurrencyTokensDesc();
        int rank = 0;
        for(User user : users){
            rank++;
            user.setRank(rank);
        }

        userRepository.saveAll(users);
    }
}
