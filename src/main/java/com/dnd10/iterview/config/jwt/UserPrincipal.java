package com.dnd10.iterview.config.jwt;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserPrincipal extends User {
    private com.dnd10.iterview.entity.User user;

    public UserPrincipal(com.dnd10.iterview.entity.User user) {
        super(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.user = user;
    }
}