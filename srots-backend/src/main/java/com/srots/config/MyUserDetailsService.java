package com.srots.config;

import com.srots.model.User;
import com.srots.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.LockedException;
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

		// \u2705 Block Login When HOLD
		if (user.getRole() == User.Role.STUDENT) {
			com.srots.model.Student student = studentRepository.findById(user.getId()).orElse(null);
			String status = student.getAccountStatus();
			if (status != null && "HOLD".equalsIgnoreCase(status)) {
				throw new LockedException("Account on HOLD. Please recharge premium.");
			}
		}

		return new UserInfoUserDetails(user);
	}
}
