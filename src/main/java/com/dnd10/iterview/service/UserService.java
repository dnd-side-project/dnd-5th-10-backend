package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.UserDto;

public interface UserService {
  UserDto getUserDetail(Long id);

  Long update(Long id, UserDto userDto);
}
