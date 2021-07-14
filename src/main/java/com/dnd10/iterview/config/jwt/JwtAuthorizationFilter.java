package com.dnd10.iterview.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    /*public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // expired token: 401 UNAUTHORIZED
        DecodedJWT decode = JWT.decode(token);
        if (decode.getExpiresAt().before(new Date(System.currentTimeMillis()))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        JWTVerifier build = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET)).build();

        String email = build.verify(token).getClaim("email").asString();

        Map<String, Object> attributes = new HashMap<>();
        for (Map.Entry<String, Claim> entry : decode.getClaims().entrySet()) {
            attributes.put(entry.getKey(), (Object) entry.getValue());
        }

        if (email != null) {
            User user = userRepository.findByEmail(email).get();
            UserPrincipal userAccount = new UserPrincipal(user);
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    userAccount,
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
/*
            OAuth2User userDetails = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")), attributes, "iterview");
            OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, List.of(new SimpleGrantedAuthority("ROLE_USER")), "iterview");

            authentication.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication);*/
        }
        chain.doFilter(request, response);
    }
}
