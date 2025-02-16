package com.msa.user.adapter.in.web.filter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.common.utils.JwtUtil;
import com.msa.user.adapter.in.web.dto.request.UserLoginRequest;
import com.msa.user.application.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import java.io.PrintWriter;
import java.util.ArrayList;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import com.msa.user.exception.InvalidLoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class AuthenticationFilterTest {

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    private final Long EXPIRED_MS = 24 * 60 * 60 * 1000L;


    @Test
    @DisplayName("올바른 로그인 요청으로 인증을 시도하면 인증이 성공한다.")
    void attemptAuthentication_Success() throws IOException {
        // Given
        UserLoginRequest loginRequest = new UserLoginRequest("test@example.com", "password");
        when(objectMapper.readValue(any(String.class), eq(UserLoginRequest.class))).thenReturn(loginRequest);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
            .thenReturn(mock(Authentication.class));

        // When
        Authentication result = authenticationFilter.attemptAuthentication(request, response);

        // Then
        assertNotNull(result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("잘못된 로그인 요청으로 인증을 시도하면 InvalidLoginException 발생행한다.")
    void attemptAuthentication_InvalidLogin() throws IOException {
        // Given
        when(request.getInputStream()).thenThrow(new InvalidLoginException());

        // When & Then
        assertThrows(InvalidLoginException.class, () -> authenticationFilter.attemptAuthentication(request, response));
    }

    @Test
    @DisplayName("잘못된 형태의 로그인 요청으로 인증을 시도하면 InvalidLoginException 발생행한다.")
    void attemptAuthenticationWithInvalidJson_InvalidLogin() throws IOException {
        // Given
        when(objectMapper.readValue(any(String.class), eq(UserLoginRequest.class))).thenThrow(new InvalidLoginException());

        // When & Then
        assertThrows(InvalidLoginException.class, () -> authenticationFilter.attemptAuthentication(request, response));
    }

    @Test
    @DisplayName("인증이 성공했을 때, JWT를 생성하면 JWT 를 응답 헤더 추가한다.")
    void successfulAuthentication() throws Exception {
        // Given
        User user = new User("tset@example.com", "password", new ArrayList<>());
        Authentication authResult = mock(Authentication.class);
        when(authResult.getPrincipal()).thenReturn(user);

        com.msa.user.domain.User domainUser = com.msa.user.domain.User.builder()
            .userId(1L)
            .build();

        when(customUserDetailsService.getUserByEmail("tset@example.com")).thenReturn(domainUser).thenReturn(domainUser);
        when(jwtUtil.createJwt(1L, EXPIRED_MS)).thenReturn("mockJwt");

        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // When
        authenticationFilter.successfulAuthentication(request, response, filterChain, authResult);

        // Then
        verify(response).addHeader("Authorization", "mockJwt");
        verify(response).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    @DisplayName("인증이 실패했을 때, 에러를 처리하면 상태 코드가 401이고 에러 메시지를 리턴한다.")
    void unsuccessfulAuthentication() throws Exception{
        // Given
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // When
        authenticationFilter.unsuccessfulAuthentication(request, response, mock(AuthenticationException.class));

        // Then
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}