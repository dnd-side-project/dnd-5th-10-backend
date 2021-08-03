package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.UserDto;
import java.security.Principal;

public interface UserService {

  UserDto getUserDetail(Principal principal);

  UserDto getUserDetail(Long id);

  Long update(Long id, UserDto userDto);
}
