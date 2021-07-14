package com.dnd10.iterview.config;

//import com.dnd10.iterview.config.jwt.JwtAuthenticationFilter;
import com.dnd10.iterview.config.oauth2.CustomOAuth2SuccessHandler;
import com.dnd10.iterview.config.oauth2.CustomOAuth2UserService;
import com.dnd10.iterview.config.jwt.JwtAuthorizationFilter;
import com.dnd10.iterview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    private final UserRepository userRepository;
    private final CorsConfig corsConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       // todo: security 설정 및 oauth2 인증 설정
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .formLogin().disable()
                .httpBasic().disable();
        http
                //.addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/", "/login", "/error", "/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(customOAuth2UserService)
                .and()
                .successHandler(customOAuth2SuccessHandler);

        http.addFilterBefore(new JwtAuthorizationFilter(/*authenticationManager(), userRepository*/), UsernamePasswordAuthenticationFilter.class);

        /*http
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize");
                //.and()
                //.userInfoEndpoint()
                //.userService(customOAuth2UserService);*/
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addExposedHeader("Authorization");
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web.ignoring()
                .mvcMatchers("/favicon.ico","/resources/**","/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
