package com.srots.scheduler;

import com.srots.model.Student;
import com.srots.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PremiumExpiryScheduler {

    private final StudentRepository studentRepository;

    @Scheduled(cron = "0 0 1 * * ?") // daily at 1 AM
    @Transactional
    public void checkPremiumExpiry() {

        List<Student> students = studentRepository.findByPremiumActiveTrueAndPremiumExpiryDateIsNotNull();

        LocalDate today = LocalDate.now();

        for (Student s : students) {
            if (s.getPremiumExpiryDate().isBefore(today)) {

                s.setPremiumActive(false);
                s.setAccountStatus("HOLD");
                // We rely on Transactional + Dirty Checking, but saveAll is also fine
            }
        }

        if (students != null && !students.isEmpty()) {
            studentRepository.saveAll(students); // Explicitly save all changes
        }
    }
}
