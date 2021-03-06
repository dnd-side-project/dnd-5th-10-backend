package com.dnd10.iterview.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.repository.UserRepository;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    public JwtAuthorizationFilter(JwtProperties jwtProperties,
        UserRepository userRepository) {
        this.jwtProperties = jwtProperties;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        //TODO:: table에서 유저 삭제시, 해당 유저 토큰으로 진입하면 필터 통과됨
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
        JWTVerifier build = JWT.require(Algorithm.HMAC256(jwtProperties.getSecret())).build();

        String email = build.verify(token).getClaim("email").asString();

        if (email != null) {
            final Optional<User> oUser = userRepository.findUserByEmail(email);
            final User user = oUser.orElse(User.builder().build());
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    "",
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
            SecurityContextHolder.getContext().setAuthentication(userToken);

/*
            OAuth2User userDetails = new DefaultOAuth2User(List.of(new SimpleGrantedAuthority("ROLE_USER")), attributes, "iterview");
            OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, List.of(new SimpleGrantedAuthority("ROLE_USER")), "iterview");

            authentication.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(authentication); */
        }
        chain.doFilter(request, response);
    }
}
