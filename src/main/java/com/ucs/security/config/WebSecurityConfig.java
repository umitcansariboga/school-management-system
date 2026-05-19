package com.ucs.security.config;

import com.ucs.security.jwt.AuthEntryPointJwt;
import com.ucs.security.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthEntryPointJwt unauthorizedHandler;
    private final UserDetailsService userDetailsService;
    private final AuthTokenFilter authTokenFilter;
    private final MessageSource messageSource;

    private static final String[] AUTH_WHITE_LIST = {
            "/",
            "/index.html",
            "/error",
            "/image/**",
            "/css/**",
            "/js/**",
            "/auth/login",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MessageSource messageSource) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(unauthorizedHandler)
                                .accessDeniedHandler((request,
                                                      response,
                                                      accessDeniedException) -> {
                                    String errorMessage = messageSource.getMessage(
                                            "error.access.denied",
                                            null,
                                            LocaleContextHolder.getLocale());

                                    response.setStatus(HttpStatus.FORBIDDEN.value());
                                    response.setContentType("application/json;charset=UTF-8");

                                    String jsonResponse = String.format(
                                            "{\"message\": \"%s\", \"success\": false, \"errorCode\": \"403\", \"path\": \"%s\", \"timeStamp\": \"%s\"}",
                                            errorMessage,
                                            request.getRequestURI(),
                                            java.time.LocalDateTime.now()
                                    );
                                    response.getWriter().write(jsonResponse);
                                })
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH_WHITE_LIST).permitAll()
                        .requestMatchers(HttpMethod.POST, "/contact-messages").permitAll()
                        .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider autProvider = new DaoAuthenticationProvider(userDetailsService);
        autProvider.setPasswordEncoder(passwordEncoder());
        return autProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
