package com.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.persintence.entities.PermissionEntity;
import com.app.persintence.entities.UserEntity;
import com.app.persintence.repository.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findUserEntityByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("El usuario " + username + " no existe."));
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		userEntity.getRoles()
		.forEach(role-> authorityList.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleEnum().name())));
		
		userEntity.getRoles().stream()
		.flatMap(role->role.getPermissions().stream())
		.forEach(PermissionEntity -> authorityList.add(new SimpleGrantedAuthority(PermissionEntity.getName())));
		
		return new User(userEntity.getUsername(),
				userEntity.getPassword(),
				userEntity.isEnabled(),
				userEntity.isAccountNoExpired(),
				userEntity.isCredentialNoExpired(),
				userEntity.isAccountNoLocked(),
				authorityList);
	}

}
