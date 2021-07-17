package com.dnd10.iterview.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/login")
  public ResponseEntity<Object> test(HttpServletRequest request, HttpServletResponse response) {

    return ResponseEntity.ok("");
  }
}
