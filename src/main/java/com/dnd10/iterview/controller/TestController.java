package com.dnd10.iterview.controller;

import com.dnd10.iterview.config.CurrentUser;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

  @Autowired
  UserRepository u;

  @GetMapping("/login")
  public ResponseEntity<Object> test(HttpServletRequest request, HttpServletResponse response) {

    return ResponseEntity.ok("loginPage");
  }
  @GetMapping("/")
  public ResponseEntity<Object> home(HttpServletRequest request,HttpServletResponse response) {
    log.info(response.getHeader("Authorization"));
    log.info(request.getHeader("Authorization"));
    return ResponseEntity.ok(request.getCookies());
  }
  @GetMapping("/prohibited")
  public ResponseEntity<Object> prohibited(HttpServletRequest request,HttpServletResponse response) {
    log.info(response.getHeader("Authorization"));
    log.info(request.getHeader("Authorization"));
    return ResponseEntity.ok(request.getCookies());
  }

  @ApiOperation(value = "currentuser 테스트", notes = "어노테이션이 잘 되는지 확인하기")
  @GetMapping("/test")
  public ResponseEntity getUserProfile(Principal p) {

    User user = u.findUserByEmail(p.getName()).get();
    return ResponseEntity.ok(user.getUsername());
  }
}
