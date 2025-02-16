package com.msa.user.adapter.in.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.response.ApiResponse;
import com.msa.common.utils.JwtUtil;
import com.msa.user.adapter.in.web.dto.request.UserLoginRequest;
import com.msa.user.application.service.CustomUserDetailsService;
import com.msa.user.exception.InvalidLoginException;
import com.msa.user.exception.UserErrorCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final CustomUserDetailsService customUserDetailsService;

    // TODO: 안보이도록 처리해야 함.
    private final Long EXPIRED_MS = 24 * 60 * 60 * 1000L;

    public AuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, ObjectMapper objectMapper, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLoginRequest loginRequest = null;

        try {
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

            loginRequest = objectMapper.readValue(messageBody, UserLoginRequest.class);
        } catch (IOException e) {
            throw new InvalidLoginException();
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String email = ((User)authResult.getPrincipal()).getUsername();

        com.msa.user.domain.User user = customUserDetailsService.getUserByEmail(email);

        String jwt = jwtUtil.createJwt(user.getUserId(), EXPIRED_MS);

        ApiResponse<Void> body = ApiResponse.success();

        response.addHeader("Authorization",  jwt);
        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ApiResponse<Void> errorResponse = ApiResponse.failure(
            UserErrorCode.INVALID_LOGIN_REQUEST_ERROR
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
