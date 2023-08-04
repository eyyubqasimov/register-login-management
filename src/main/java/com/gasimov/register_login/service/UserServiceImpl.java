package com.gasimov.register_login.service;

import com.gasimov.register_login.model.Role;
import com.gasimov.register_login.model.User;
import com.gasimov.register_login.repository.UserRepository;
import com.gasimov.register_login.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(
                registrationDto.getFirstName(),
                registrationDto.getLastName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                getRolesForRegistration(registrationDto.getEmail())
        );

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Geçersiz kullanıcı adı veya şifre.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRolesToAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public void deleteUserRole(String userEmail, String roleName) {
        User user = userRepository.findByEmail(userEmail);
        if (user != null) {
            Role roleToDelete = user.getRoles().stream()
                    .filter(role -> role.getName().equalsIgnoreCase(roleName))
                    .findFirst()
                    .orElse(null);

            if (roleToDelete != null) {
                user.deleteRole(roleToDelete);
                userRepository.save(user);
            }
        }
    }

    @Override
    public void deleteUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user != null) {
            userRepository.deleteById((id));
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    private List<Role> getRolesForRegistration(String email) {
        if (email.equalsIgnoreCase("eyyub.qasimov98@gmail.com")) {
            return Arrays.asList(new Role("ROLE_ADMIN"));
        } else {
            return Arrays.asList(new Role("ROLE_USER"));
        }
    }
}
