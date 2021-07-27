/*package com.dnd10.iterview.config.jwt;

import com.dnd10.iterview.entity.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {
    private Long id;
    private String email;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String username, Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.email = email;
      this.username = username;
      this.authorities = authorities;
    }

    public static UserPrincipal create(User user){
      List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

      return new UserPrincipal(
          user.getId(),
          user.getEmail(),
          user.getUsername(),
          authorities
      );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes){
      UserPrincipal userPrincipal = UserPrincipal.create(user);
      userPrincipal.setAttributes(attributes);

      return userPrincipal;
    }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String getName() {
    return String.valueOf(id);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
      return authorities;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes){
      this.attributes = attributes;
  }
}*/