package com.dnd10.iterview.config.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final OAuthProperties oAuthProperties;
  public static final String REDIRECT_PATH = "/login";

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    final ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> data = new HashMap<>();

    data.put(
        "timestamp",
        Calendar.getInstance().getTime());
    data.put(
        "exception",
        exception.getMessage());

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json;charset=UTF-8");

    getRedirectStrategy().sendRedirect(request, response, oAuthProperties.getRedirectUrl() + REDIRECT_PATH);
  }
}
