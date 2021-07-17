package com.dnd10.iterview.config.oauth2;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dnd10.iterview.config.jwt.JwtProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (logger.isDebugEnabled()) {
            logger.debug(authentication.getPrincipal());
        }

        DefaultOAuth2User principal = (DefaultOAuth2User) authentication.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject(principal.getAttribute("email"))
                .withExpiresAt(new Date(System.currentTimeMillis()+ JwtProperties.EXPIRATION_TIME))
                .withClaim("id", (Long) principal.getAttribute("id"))
                .withClaim("email", (String) principal.getAttribute("email"))
                .sign(Algorithm.HMAC256(JwtProperties.SECRET));
        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

        getRedirectStrategy().sendRedirect(request, response, "http://localhost:8080/");

    }
}
