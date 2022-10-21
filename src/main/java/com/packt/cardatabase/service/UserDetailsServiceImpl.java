package com.packt.cardatabase.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packt.cardatabase.domain.Userr;
import com.packt.cardatabase.domain.UserrRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserrRepository userrRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userrname) throws UsernameNotFoundException {
		Optional<Userr> userr = userrRepository.findByUserrname(userrname);
		UserBuilder builder = null;
		if(userr.isPresent()) {
			Userr currentUserr = userr.get();
			builder = User.withUsername(userrname)
					.password(currentUserr.getPassword())
					.roles(currentUserr.getRole());
		} else {
			throw new UsernameNotFoundException("User not found.");
		}
		return builder.build();
	}
}
