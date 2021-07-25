package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class UserDto {
    // todo: jpa에서 순환참조 발생할 수 있으니 반환시에는 웬만하면 dto로

    private String email;
    private String username;
    private String password;
    private AuthProvider provider;
    private String providerId;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
    }

    public UserDto(String email, String username, String password,AuthProvider authProvider, String providerId) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.provider = authProvider;
        this.providerId = providerId;
    }

    public User toEntity() {
        return User.builder()
            .email(email)
            .username(username)
            .password(password)
            .provider(provider)
            .providerId(providerId)
            .build();
    }
}
