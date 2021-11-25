package by.tech.project_management_app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import by.tech.project_management_app.entities.SecurityUser;
import by.tech.project_management_app.entities.User;
import by.tech.project_management_app.repository.RoleRepository;
import by.tech.project_management_app.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User was not found in the database");
        }
        List<String> authorities = roleRepository.getRolesById(user.getId());
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (authorities != null) {
            authorities.stream().forEach(authority -> grantList.add(new SimpleGrantedAuthority(authority)));
        }
        SecurityUser securityUser = new SecurityUser(user, grantList);
        UserDetails userDetails = (UserDetails) securityUser;
        return userDetails;
    }

}