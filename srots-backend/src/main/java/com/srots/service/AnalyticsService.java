package com.srots.service;

import com.srots.dto.analytics.AnalyticsOverviewDTO;
import com.srots.dto.analytics.SystemAnalyticsDTO;

public interface AnalyticsService {
    AnalyticsOverviewDTO getOverview();

    SystemAnalyticsDTO getSystemAnalytics();
}
