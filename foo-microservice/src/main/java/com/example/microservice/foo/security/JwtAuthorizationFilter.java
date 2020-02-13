package com.example.microservice.foo.security;

import com.example.microservice.foo.security.model.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final String BEARER_PREFIX = "Bearer ";
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            JwtAuthentication jwtAuthentication = new JwtAuthentication();
            jwtAuthentication.setJwt(token.substring(BEARER_PREFIX.length()));

            SecurityContextHolder.getContext().setAuthentication(
                    authenticationProvider.authenticate(jwtAuthentication));
        } else {
            throw new RuntimeException("User unauthorized");
        }

        filterChain.doFilter(request, response);
    }
}
