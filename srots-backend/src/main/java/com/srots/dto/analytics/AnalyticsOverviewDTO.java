package com.srots.dto.analytics;

import com.srots.dto.jobdto.JobResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalyticsOverviewDTO {
    private List<BranchDistributionDTO> branchDistribution;
    private List<PlacementProgressDTO> placementProgress;
    private List<JobTypeDTO> jobTypes;
    private StatsDTO stats;
    private List<JobResponseDTO> recentJobs;
}
