package com.adam.evaluaretehnica.user;

import com.adam.evaluaretehnica.http.ETResponse;
import com.adam.evaluaretehnica.user.http.RankingResponse;
import com.adam.evaluaretehnica.user.http.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ETResponse> getCurrentUserData(){
        User currentUser = userService.getCurrentUser();
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/users/current-user",
                        new UserResponse(
                                currentUser.getUsername(),
                                currentUser.getCurrencyTokens(),
                                currentUser.getRank()
                        )
                )
        );
    }

    @GetMapping("/ranking")
    public ResponseEntity<ETResponse> getRanking(){
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/users/ranking",
                        userService.getUserRanks())
                );

    }
}
