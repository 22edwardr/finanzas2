package com.robayo.edward.finances.app.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.robayo.edward.finances.app.models.CustomUser;
import com.robayo.edward.finances.app.repository.ICustomUserDetailsDao;

@Service
@Qualifier("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ICustomUserDetailsDao customUserDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser customUser;

		customUser = customUserDao.loadUserByUsername(username);

		if (customUser != null) {
			List<String> roles;
			Collection<GrantedAuthority> grantedAuthorities;

			roles = customUserDao.loadRolesByUsername(username);
			grantedAuthorities = new HashSet<>();

			if (roles != null && roles.size() > 0) {

				roles.forEach(rol -> {
					grantedAuthorities.add(new SimpleGrantedAuthority(rol));
				});

			}
			
			return new CustomUser(customUser.getNombre(), customUser.getId(), grantedAuthorities,
					customUser.getPassword(), customUser.getUsername(), true, true, true, customUser.isEnabled());
		}

		throw new UsernameNotFoundException("usernameNotFound");
	}

}
