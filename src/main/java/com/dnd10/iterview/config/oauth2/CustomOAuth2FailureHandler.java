package com.dnd10.iterview.config.oauth2;

import com.dnd10.iterview.config.jwt.JwtProperties;
import com.dnd10.iterview.util.CookieUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    ObjectMapper objectMapper = new ObjectMapper();

    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    Map<String, Object> data = new HashMap<>();
    data.put(
        "timestamp",
        Calendar.getInstance().getTime());
    data.put(
        "exception",
        exception.getMessage());

    response.getOutputStream()
        .println(objectMapper.writeValueAsString(data));
  }
}
