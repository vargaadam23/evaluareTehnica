package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.badge.BadgeRepository;
import com.adam.evaluaretehnica.badge.http.BadgeResponse;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.user.http.RankingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsersBasedOnIdList(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    public User getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(user.getId()).orElseThrow();
    }

    public RankingResponse getUserRanks(){
        return new RankingResponse(userRepository.findRankedEntityByOrderByCurrencyTokensDesc());
    }

    //Make a separate response for the user badges
    public List<BadgeResponse> getUserBadges(){
        User user = getCurrentUser();
        List<BadgeResponse> badgeResponses = new ArrayList<>();
        for (Badge badge : user.getBadges()){
            badgeResponses.add(new BadgeResponse(
                    badge.getTitle(),
                    badge.getConditionDescription(),
                    badge.getCssStyles()
            ));
        }

        return badgeResponses;
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
