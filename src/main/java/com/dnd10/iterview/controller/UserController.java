package com.dnd10.iterview.controller;


import com.dnd10.iterview.dto.UserDto;
import com.dnd10.iterview.service.UserService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
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

  @ApiOperation(value = "회원 프로필 조회", notes = "<big>회원프로필</big>을 반환한다.")
  @GetMapping("profile/{userId}")
  public UserDto getUserProfile(@PathVariable Long userId) {
    return userService.getUserDetail(userId);
  }

  @ApiOperation(value = "회원 유저네임 변경", notes = "<big>회원의 이름</big>을 업데이트한다.")
  @PutMapping("/{userId}")
  public ResponseEntity<Long> updateUserName(@PathVariable Long userId, @RequestBody @Valid UserDto userDto) {
    final Long id = userService.update(userId, userDto);
    return ResponseEntity.ok(id);
  }
}
