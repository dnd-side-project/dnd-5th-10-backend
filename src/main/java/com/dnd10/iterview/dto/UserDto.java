package com.dnd10.iterview.dto;

import com.dnd10.iterview.entity.AuthProvider;
import com.dnd10.iterview.entity.User;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
public class UserDto {

    @Email
    private String email;
    @NotNull
    @Size(max = 20)
    private String username;
    @ApiModelProperty(value = "oauth2 제공자", allowableValues = "google,github")
    private AuthProvider provider;
    private String providerId;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.provider = user.getProvider();
        this.providerId = user.getProviderId();
    }

    public UserDto(String email, String username,AuthProvider authProvider, String providerId) {
        this.email = email;
        this.username = username;
        this.provider = authProvider;
        this.providerId = providerId;
    }

    public User toEntity() {
        return User.builder()
            .email(email)
            .username(username)
            .provider(provider)
            .providerId(providerId)
            .build();
    }
}
