package com.tom.javaspring.security;

import com.tom.javaspring.dao.UserDAO;
import com.tom.javaspring.entity.UserEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity userEntity = userDAO.findByUserName(userName);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = new User(userEntity.getUserName(),
                            userEntity.getPassword(),
                            userEntity.getRoles()
                                    .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                                    .collect(Collectors.toList()));
        return user;
    }
}
