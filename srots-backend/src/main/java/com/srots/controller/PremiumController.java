package com.srots.controller;

import com.srots.config.UserInfoUserDetails;
import com.srots.dto.PremiumResponseDTO;
import com.srots.dto.PremiumSubscribeRequestDTO;
import com.srots.service.PremiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/premium")
@RequiredArgsConstructor
public class PremiumController {

    private final PremiumService premiumService;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
            @jakarta.validation.Valid @RequestBody PremiumSubscribeRequestDTO dto,
            @AuthenticationPrincipal UserInfoUserDetails user) {

        PremiumResponseDTO response = premiumService.activatePremium(user.getUserId(), dto);

        return ResponseEntity.ok(response);
    }
}
