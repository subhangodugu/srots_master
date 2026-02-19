package com.srots.repository;

import com.srots.dto.analytics.PlacementProgressDTO;
import com.srots.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationAnalyticsRepository extends JpaRepository<Application, String> {

    @Query("""
                SELECT new com.srots.dto.analytics.PlacementProgressDTO(
                    CAST(FUNCTION('MONTHNAME', a.placedAt) AS string),
                    COUNT(a)
                )
                FROM Application a
                WHERE a.status = 'PLACED' AND a.placedAt IS NOT NULL
                GROUP BY a.placedAt
            """)
    List<PlacementProgressDTO> getMonthlyPlacements();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'STUDENT'")
    Long countTotalStudents();

    @Query("SELECT COUNT(DISTINCT a.student.id) FROM Application a WHERE a.status = 'PLACED'")
    Long countPlacedStudents();
}
