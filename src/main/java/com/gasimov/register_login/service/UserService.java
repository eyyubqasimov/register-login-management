package com.gasimov.register_login.service;
import com.gasimov.register_login.model.Role;
import com.gasimov.register_login.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import com.gasimov.register_login.model.User;

import java.util.List;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	List<User> getAllUsers();
	List<Role> getAllRoles();


	void deleteUserRole(String userEmail, String roleName);


	List<User> findAll();
}
