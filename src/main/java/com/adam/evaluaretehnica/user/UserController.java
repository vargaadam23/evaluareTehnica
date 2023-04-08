package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.user.http.RankingResponse;
import com.adam.evaluaretehnica.user.http.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/current-user")
    public ResponseEntity<UserResponse> getCurrentUserData(){
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(
                new UserResponse(
                        currentUser.getUsername(),
                        currentUser.getCurrencyTokens(),
                        currentUser.getRank()
                )
        );
    }

    @GetMapping("/ranking")
    public ResponseEntity<RankingResponse> getRanking(){
        return ResponseEntity.ok(userService.getUserRanks());
    }
}
