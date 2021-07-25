package com.dnd10.iterview.service;

import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }


  @Override
  public UserDto getUserDetail(Long id) {
    final User user = userRepository.findUserById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다." + id));
    return new UserDto(user);
  }

  @Transactional
  @Override
  public Long update(Long id, UserDto userDto) {
    // transaction 내에서 영속성 컨텍스트 유지되기때문에 트랜잭션 끝나면 엔티티 업데이트가 커밋됨!
    final User user = userRepository.findUserById(id)
        .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다." + id));
    user.update(userDto.getUsername());
    return user.getId();
  }
}
