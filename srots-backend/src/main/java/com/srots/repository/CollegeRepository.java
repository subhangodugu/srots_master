package com.srots.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.srots.model.College;

import com.srots.dto.analytics.LeaderboardDTO;

@Repository
public interface CollegeRepository extends JpaRepository<College, String> {

    @Query("SELECT c FROM College c WHERE :q IS NULL OR " +
            "LOWER(c.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(c.code) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<College> searchColleges(@Param("q") String query);

    boolean existsByCode(String code);

    @Query("""
                SELECT new com.srots.dto.analytics.LeaderboardDTO(
                    c.name,
                    (SELECT COUNT(a) FROM Application a WHERE a.student.college.id = c.id AND a.status = 'PLACED'),
                    (SELECT COUNT(j) FROM Job j WHERE j.college.id = c.id)
                )
                FROM College c
                ORDER BY (SELECT COUNT(a) FROM Application a WHERE a.student.college.id = c.id AND a.status = 'PLACED') DESC
            """)
    List<LeaderboardDTO> getLeaderboard();
}