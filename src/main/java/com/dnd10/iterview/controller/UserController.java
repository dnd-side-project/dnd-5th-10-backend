package com.dnd10.iterview.controller;


import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  //TODO:: PutMapping 405 ERROR 확인
  /**
   * 컨트롤러 리턴은 무조건 Dto
   * 회원정보및 프로필버튼 -> 회원정보수정 닉네임 유저태그설정 O
   * 내가 한 질문
   * 내가 한 답변
   * 좋아요 한 답변
   * 북마크 한 질문
   */

  UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping("profile/{userId}")
  public UserDto getUserProfile(@PathVariable Long userId) {
    return userService.getUserDetail(userId);
  }

  @PutMapping("/{userId}")
  public ResponseEntity<Long> updateUserName(@PathVariable Long userId, @RequestBody UserDto userDto) {
    final Long id = userService.update(userId, userDto);
    return ResponseEntity.ok(id);
  }
}
