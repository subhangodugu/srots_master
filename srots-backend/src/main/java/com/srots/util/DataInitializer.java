package com.srots.util;

import com.srots.model.*;
import com.srots.repository.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepo;
    private final StudentProfileRepository profileRepo;
    private final JobRepository jobRepo;
    private final ApplicationRepository appRepo;
    private final CollegeRepository collegeRepo;

    @PostConstruct
    public void init() {
        if (userRepo.count() > 0)
            return;

        // Create a dummy college
        College college = new College();
        college.setId(UUID.randomUUID().toString());
        college.setName("Demo Engineering College");
        collegeRepo.save(college);

        // Create Students
        String[] branches = { "CSE", "ECE", "CSE", "MECH", "EEE", "CSE" };
        String[] names = { "Rahul", "Anjali", "Vikram", "Sneha", "Arjun", "Pooja" };

        for (int i = 0; i < names.length; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString());
            user.setUsername(names[i].toLowerCase() + (i + 1));
            user.setEmail(names[i].toLowerCase() + "@example.com");
            user.setPasswordHash("password"); // Not real auth for dummy data
            user.setFullName(names[i]);
            user.setRole(User.Role.STUDENT);
            user.setCollege(college);
            userRepo.save(user);

            StudentProfile profile = new StudentProfile();
            profile.setUser(user);
            profile.setBranch(branches[i]);
            profileRepo.save(profile);

            // Create some jobs and applications
            if (i < 4) {
                Job job = new Job();
                job.setId(UUID.randomUUID().toString());
                job.setCollege(college);
                job.setTitle(i % 2 == 0 ? "Software Engineer" : "Analyst");
                job.setCompanyName(i == 0 ? "TCS" : (i == 1 ? "Infosys" : (i == 2 ? "Wipro" : "Accenture")));
                job.setType(i == 2 ? Job.JobType.INTERNSHIP : Job.JobType.FULL_TIME);
                job.setPostedAt(LocalDateTime.now().minusDays(10 + i));
                jobRepo.save(job);

                Application app = new Application();
                app.setJob(job);
                app.setStudent(user);
                app.setAppliedAt(LocalDateTime.now().minusDays(5 + i));

                if (i != 3) {
                    app.setStatus(Application.AppStatus.PLACED);
                    app.setPlacedAt(LocalDateTime.now().minusDays(2 + i));
                } else {
                    app.setStatus(Application.AppStatus.Applied);
                }
                appRepo.save(app);
            }
        }
    }
}
