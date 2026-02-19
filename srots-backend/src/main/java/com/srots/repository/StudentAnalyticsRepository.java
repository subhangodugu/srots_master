package com.srots.repository;

import com.srots.dto.analytics.BranchDistributionDTO;
import com.srots.model.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAnalyticsRepository extends JpaRepository<StudentProfile, String> {

    @Query("""
                SELECT new com.srots.dto.analytics.BranchDistributionDTO(
                    sp.branch, COUNT(sp)
                )
                FROM StudentProfile sp
                WHERE sp.branch IS NOT NULL
                GROUP BY sp.branch
            """)
    List<BranchDistributionDTO> getBranchDistribution();
}
