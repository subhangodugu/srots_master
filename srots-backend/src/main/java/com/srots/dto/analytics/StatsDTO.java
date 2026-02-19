package com.srots.dto.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatsDTO {
    private Long totalStudents;
    private Long placedStudents;
    private Long companiesVisited;
    private Double placementRate;
}
