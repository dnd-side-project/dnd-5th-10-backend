package com.dnd10.iterview.config.oauth2;

import java.io.IOException;
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
  public static final String ERROR_QUERY_PARAMETER = "?error=login";

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException {

    getRedirectStrategy().sendRedirect(request, response, oAuthProperties.getRedirectUrl() + ERROR_QUERY_PARAMETER);
  }
}
