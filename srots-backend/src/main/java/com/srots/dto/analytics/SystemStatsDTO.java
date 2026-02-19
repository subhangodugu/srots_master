package com.srots.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemStatsDTO {
    private Long totalColleges;
    private Long activeStudents;
    private Long expiringAccounts;
    private Long totalJobs;
}
