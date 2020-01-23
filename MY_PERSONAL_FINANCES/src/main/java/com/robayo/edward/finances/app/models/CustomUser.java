package com.robayo.edward.finances.app.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUser implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Long id;
	private Collection<? extends GrantedAuthority> authorities;
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	public CustomUser(String nombre, Long id, Collection<? extends GrantedAuthority> authorities, String password,
			String username, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired,
			boolean enabled) {
		super();
		this.nombre = nombre;
		this.id = id;
		this.authorities = authorities;
		this.password = password;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}
	public String getNombre() {
		return nombre;
	}
	public Long getId() {
		return id;
	}
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public String getPassword() {
		return password;
	}
	public String getUsername() {
		return username;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}



}
