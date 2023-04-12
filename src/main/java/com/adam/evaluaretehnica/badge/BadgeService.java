package com.adam.evaluaretehnica.badge;

import com.adam.evaluaretehnica.badge.http.BadgeCreationRequest;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserRepository;
import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.util.ApplicationProperties;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BadgeService {
    private final BadgeRepository badgeRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    public List<Badge> getAllBadges(){
        return badgeRepository.findAll();
    }

    @Transactional
    public Badge createBadge(BadgeCreationRequest badgeCreationRequest) throws NotEnoughTokensException {
        User currentUser = userService.getCurrentUser();

        //Badge creation will cost tokens for the user
        currentUser.subtractCurrencyTokensFromBalance(
                Integer.parseInt(
                        applicationProperties.getConfigValue("badgeCreationCost")
                )
        );

        //Create badge instance
        Badge badge = badgeCreationRequest.badgeType().createBadge();
        badge.setValue(badgeCreationRequest.conditionValue());
        badge.setConditionDescription(badgeCreationRequest.badgeDescription());
        badge.setCssStyles(badgeCreationRequest.cssStyles());
        badge.setImportance(badgeCreationRequest.importance());
        badge.setTitle(badgeCreationRequest.title());

        //Add the badge to users that fulfill the conditions
        List<User> userList = addBadgeToEligibleUsers(badge);
        userList.add(currentUser);

        badgeRepository.save(badge);
        userRepository.saveAll(userList);

        return badge;

    }

    //Helper for badge creation, checks if users fulfill badge conditions
    public List<User> addBadgeToEligibleUsers(Badge badge){
        List<User> userList = userRepository.findAll();
        for(User user : userList){
            if(badge.isEligibleForUser(user)){
                user.addBadge(badge);
            }
        }

        return userList;
    }

    //Used when tokens are added to the users balance, checks all badges
    public void verifyAndAddBadgesToUser(User user){
        List<Badge> badgeList = badgeRepository.findAll();
        for(Badge badge : badgeList){
            if(badge.isEligibleForUser(user)){
                user.addBadgeWithCheck(badge);
            }
        }
    }
}
