package com.robayo.edward.finances.app.repository;

import java.util.List;

import com.robayo.edward.finances.app.models.CustomUser;

public interface ICustomUserDetailsDao {
	
	public CustomUser loadUserByUsername(String username);
	public List<String> loadRolesByUsername(String username);

}
