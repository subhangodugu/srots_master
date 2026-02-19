package com.srots.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemAnalyticsDTO {
    private SystemStatsDTO stats;
    private List<LeaderboardDTO> leaderboard;
}
