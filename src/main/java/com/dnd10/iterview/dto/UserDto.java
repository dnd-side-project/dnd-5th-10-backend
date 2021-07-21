package com.dnd10.iterview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    // todo: jpa에서 순환참조 발생할 수 있으니 반환시에는 웬만하면 dto로
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String username;

}
