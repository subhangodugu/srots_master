package com.srots.service.impl;

import com.srots.dto.analytics.AnalyticsOverviewDTO;
import com.srots.dto.analytics.BranchDistributionDTO;
import com.srots.dto.analytics.JobTypeDTO;
import com.srots.dto.analytics.PlacementProgressDTO;
import com.srots.dto.analytics.StatsDTO;
import com.srots.dto.analytics.SystemAnalyticsDTO;
import com.srots.dto.analytics.SystemStatsDTO;
import com.srots.dto.analytics.LeaderboardDTO;
import com.srots.model.User;
import com.srots.repository.UserRepository;
import com.srots.repository.CollegeRepository;
import com.srots.repository.ApplicationAnalyticsRepository;
import com.srots.repository.JobAnalyticsRepository;
import com.srots.repository.StudentAnalyticsRepository;
import com.srots.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {

        private final StudentAnalyticsRepository studentRepo;
        private final JobAnalyticsRepository jobRepo;
        private final ApplicationAnalyticsRepository appRepo;
        private final UserRepository userRepo;
        private final CollegeRepository collegeRepo;
        private final com.srots.service.JobMapper jobMapper;

        @Override
        public AnalyticsOverviewDTO getOverview() {
                var branchData = studentRepo.getBranchDistribution();
                var progressData = appRepo.getMonthlyPlacements();
                var jobTypes = jobRepo.getJobTypeDistribution();

                Long totalStudents = appRepo.countTotalStudents();
                Long placedStudents = appRepo.countPlacedStudents();

                Double rate = totalStudents > 0 ? (placedStudents * 100.0) / totalStudents : 0.0;

                StatsDTO stats = StatsDTO.builder()
                                .totalStudents(totalStudents)
                                .placedStudents(placedStudents)
                                .placementRate(rate)
                                .companiesVisited(25L) // Hardcoded for now as per requirement or until a better source
                                                       // is found
                                .build();

                var recentJobs = jobRepo.findTop5ByOrderByPostedAtDesc().stream()
                                .map(job -> jobMapper.toResponseDTO(job, null, "ADMIN"))
                                .toList();

                return AnalyticsOverviewDTO.builder()
                                .branchDistribution(branchData)
                                .placementProgress(progressData)
                                .jobTypes(jobTypes)
                                .stats(stats)
                                .recentJobs(recentJobs)
                                .build();
        }

        @Override
        public SystemAnalyticsDTO getSystemAnalytics() {
                long totalColleges = collegeRepo.count();
                long activeStudents = userRepo.countByRole(User.Role.STUDENT);
                long expiringAccounts = 0L; // Placeholder or calculate if logic exists
                long totalJobs = jobRepo.count();

                SystemStatsDTO stats = SystemStatsDTO.builder()
                                .totalColleges(totalColleges)
                                .activeStudents(activeStudents)
                                .expiringAccounts(expiringAccounts)
                                .totalJobs(totalJobs)
                                .build();

                List<LeaderboardDTO> leaderboard = collegeRepo.getLeaderboard();

                return SystemAnalyticsDTO.builder()
                                .stats(stats)
                                .leaderboard(leaderboard)
                                .build();
        }
}
