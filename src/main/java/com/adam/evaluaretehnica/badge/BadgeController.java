package com.adam.evaluaretehnica.badge;

import com.adam.evaluaretehnica.badge.http.BadgeCreationRequest;
import com.adam.evaluaretehnica.badge.http.BadgeListResponse;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.http.ETResponse;
import com.adam.evaluaretehnica.http.GenericResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/badges")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;

    @GetMapping
    public ResponseEntity<ETResponse> getAllBadges() {
        return ResponseEntity.ok(
                new ETResponse(
                        HttpStatus.OK,
                        "/badges",
                        new BadgeListResponse(
                                badgeService.getAllBadges()
                        ))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ETResponse> createBadge(@RequestBody @Valid BadgeCreationRequest badgeCreationRequest) throws NotEnoughTokensException {
        badgeService.createBadge(badgeCreationRequest);
        return new ResponseEntity<ETResponse>(
                new ETResponse(HttpStatus.CREATED,
                        "/badges/create",
                        new GenericResponse(
                                "Badge was successfully created!"
                        )
                ),
                HttpStatus.CREATED
        );
    }
}
