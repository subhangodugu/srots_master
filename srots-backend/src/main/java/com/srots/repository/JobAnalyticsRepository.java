package com.srots.repository;

import com.srots.dto.analytics.JobTypeDTO;
import com.srots.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAnalyticsRepository extends JpaRepository<Job, String> {

    @Query("""
                SELECT new com.srots.dto.analytics.JobTypeDTO(
                    j.type, COUNT(j)
                )
                FROM Job j
                GROUP BY j.type
            """)
    List<JobTypeDTO> getJobTypeDistribution();

    List<Job> findTop5ByOrderByPostedAtDesc();
}
