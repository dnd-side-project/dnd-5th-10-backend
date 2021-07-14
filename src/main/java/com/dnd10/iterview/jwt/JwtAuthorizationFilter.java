package com.dnd10.iterview.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dnd10.iterview.entity.User;
import com.dnd10.iterview.entity.UserPrincipal;
import com.dnd10.iterview.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

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


        if (email != null) {
            User user = userRepository.findByEmail(email);
            UserPrincipal userAccount = new UserPrincipal(user);
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                    userAccount,
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );

            SecurityContextHolder.getContext().setAuthentication(userToken);
        }
        chain.doFilter(request, response);
    }
}
