package com.dnd10.iterview.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

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
}
