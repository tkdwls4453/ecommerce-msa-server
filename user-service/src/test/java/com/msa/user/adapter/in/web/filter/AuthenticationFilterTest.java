package com.msa.user.adapter.in.web.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.utils.JwtUtil;
import com.msa.user.adapter.in.web.dto.request.UserLoginRequest;
import com.msa.user.application.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AuthenticationFilterTest {

    private AuthenticationFilter authenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationFilter = new AuthenticationFilter(authenticationManager, jwtUtil, objectMapper, customUserDetailsService);
    }

    @Test
    @DisplayName("attemptAuthentication 메서드가 올바른 로그인 요청으로 인증을 성공적으로 수행한다.")
    void attemptAuthentication_ShouldAuthenticateSuccessfully() throws IOException {
        // Given
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        UserLoginRequest loginRequest = new UserLoginRequest("test@example.com", "password");
        when(objectMapper.readValue(any(String.class), eq(UserLoginRequest.class))).thenReturn(loginRequest);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("test@example.com", "password");
        when(authenticationManager.authenticate(token)).thenReturn(mock(Authentication.class));

        // When
        authenticationFilter.attemptAuthentication(request, response);

        // Then
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}