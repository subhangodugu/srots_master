package com.srots.config;

import com.srots.model.User;
import com.srots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private com.srots.repository.StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Looking for user in DB: " + username);

		User user = repo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		System.out.println("User found! Hash in DB is: " + user.getPasswordHash());

		String accountStatus = "ACTIVE"; // Default for non-students

		// \u2705 Block Login When HOLD
		if (user.getRole() == User.Role.STUDENT) {
			com.srots.model.Student student = studentRepository.findByUserId(user.getId()).orElse(null);
			if (student == null) {
				// Lazy-create for legacy students
				student = new com.srots.model.Student();
				student.setId(java.util.UUID.randomUUID().toString());
				student.setUserId(user.getId());
				student.setName(user.getFullName());
				student.setEmail(user.getEmail());
				student.setCollegeId(user.getCollege() != null ? user.getCollege().getId() : null);
				student.setCreatedAt(java.time.LocalDateTime.now());
				studentRepository.save(student);
			}
			accountStatus = student.getAccountStatus();
		}

		return new UserInfoUserDetails(user, accountStatus);
	}
}
