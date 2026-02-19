package com.srots.controller;

import com.srots.dto.analytics.AnalyticsOverviewDTO;
import com.srots.dto.analytics.SystemAnalyticsDTO;
import com.srots.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/analytics")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowCredentials = "false")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/overview")
    public AnalyticsOverviewDTO getAnalytics() {
        return analyticsService.getOverview();
    }

    @GetMapping("/system")
    public SystemAnalyticsDTO getSystemAnalytics() {
        return analyticsService.getSystemAnalytics();
    }
}
