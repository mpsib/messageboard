package com.mpsib.messageboard.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mpsib.messageboard.domain.NewUserDetails;
import com.mpsib.messageboard.domain.User;
import com.mpsib.messageboard.domain.UserRepository;


@Service
public class UserDetailServiceImpl implements UserDetailsService{
	private final UserRepository repository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository) {
		repository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User currentUser = repository.findByUsername(username);
		UserDetails user = new org.springframework.security.core.userdetails.User
				(username, currentUser.getPasswordHash(), AuthorityUtils.createAuthorityList(currentUser.getRole()));
		return user;
	}
	
    public User save(NewUserDetails newUserDetails) {
        User user = new User(
        		newUserDetails.getUsername(),
        		passwordEncoder.encode(newUserDetails.getPassword()), 
        		"USER");

        return repository.save(user);
    }
    
    public List<User> getAll() {
        return (List<User>)repository.findAll();
    }
	
}
