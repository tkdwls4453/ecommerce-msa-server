package com.msa.common.utils;

import static org.assertj.core.api.Assertions.*;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private JwtUtil sut;
    private final String secret = "test-jwt-secret-key-qasalewimdasfwa";

    private final Long userId = 1L;

    @BeforeEach
    void setUp() {
        sut = new JwtUtil(secret);
    }

    @Test
    @DisplayName("userId와 제한 시간을 제공하면 jwt를 생성한다.")
    void createJwtTest(){
        // Given
        Long expiredMs = 60000L;

        // When
        String token = sut.createJwt(userId, expiredMs);

        // Then
        assertThat(token).isNotNull();
    }

    @Nested
    @DisplayName("토큰의 만료 여부를 확인한다.")
    class isExpiredTest{
        @Test
        @DisplayName("토큰이 만료되지 않았다면 false를 반환한다.")
        void nonExpired_ShouldReturnFalse(){
            // Given
            Long expiredMs = 60000L;
            String token = sut.createJwt(userId, expiredMs);

            // When
            Boolean result = sut.isExpired(token);

            // Then
            assertThat(result).isFalse();
        }

        @Test
        @DisplayName("토큰이 만료됐다면 true를 반환한다.")
        void Expired_ShouldReturnTrue() throws InterruptedException {
            // Given
            Long expiredMs = 10L;
            String token = sut.createJwt(userId, expiredMs);

            Thread.sleep(10L);

            // When
            Boolean result = sut.isExpired(token);

            // Then
            assertThat(result).isTrue();
        }
    }

    @Nested
    @DisplayName("토큰으로 유저 아이디 조회 테스트")
    class getUserIdTest{
        @Test
        @DisplayName("유효한 토큰으로 유저 아이디 조회시, 유저 아이디를 반환한다.")
        void getUserIdThenReturnUserId(){
            // Given
            Long expiredMs = 60000L;
            String token = sut.createJwt(userId, expiredMs);

            // When
            Long result = sut.getUserId(token);

            // Then
            assertThat(result).isEqualTo(userId);
        }

        @Test
        @DisplayName("잘못된 형태의 토큰으로 유저 아이디 조회시 예외를 반환한다.")
        void getUserIdThenThrowException(){
            // Given
            Long expiredMs = 60000L;
            String invalidToken = "invalid.token.value";

            // When Then
            assertThatThrownBy(() -> sut.getUserId(invalidToken))
                .isInstanceOf(MalformedJwtException.class);
        }

        @Test
        @DisplayName("유효하지 않은 토큰으로 유저 아이디 조회시 예외를 반환한다.")
        void getUserIdThenThrowException2() throws InterruptedException {
            // Given
            Long expiredMs = 10L;
            String token = sut.createJwt(userId, expiredMs);

            Thread.sleep(10L);

            // When Then
            assertThatThrownBy(() -> sut.getUserId(token))
                .isInstanceOf(ExpiredJwtException.class);
        }
    }

}