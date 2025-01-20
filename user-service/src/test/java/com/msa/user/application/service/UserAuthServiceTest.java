package com.msa.user.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.user.application.port.in.UserRegisterCommand;
import com.msa.user.application.port.out.UserRegisterPort;
import com.msa.user.domain.User;
import com.msa.user.domain.UserFixtures;
import com.msa.user.exception.DuplicateEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * register(userRegisterCommand)
 *  - 유저 정보로 도메인을 생성한다.
 *  - 중복 이메일 검사를 한다.
 *  - 도메인 생성 시, 패스워드를 암호화 한다.
 *  - 유저 도메인을 데이터베이스에 저장한다.
 */
@ExtendWith(MockitoExtension.class)
class UserAuthServiceTest {

    @InjectMocks
    private UserAuthService sut;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRegisterPort userRegisterPort;


    @DisplayName("[service] 회원가입 로직 테스트")
    @Nested
    class register{

        @Test
        @DisplayName("회원가입 성공 시 암호화된 비밀번호로 유저를 생성")
        void shouldEncryptPasswordAndCreateUser() {
            // Given
            UserRegisterCommand command = UserFixtures.registerUserCommand();

            String encryptedPassword = "encryptedPassword";
            User savedUser = UserFixtures.user(command.name(), command.email(), encryptedPassword);

            when(bCryptPasswordEncoder.encode(command.password())).thenReturn(encryptedPassword);
            when(userRegisterPort.existsByEmail(any(String.class))).thenReturn(false);
            when(userRegisterPort.save(any(User.class))).thenReturn(savedUser);

            // When
            User result = sut.register(command);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(savedUser.getName());
            assertThat(result.getEmail()).isEqualTo(savedUser.getEmail());
            assertThat(result.getPassword()).isEqualTo(savedUser.getPassword());

            verify(userRegisterPort).save(any(User.class));
            verify(bCryptPasswordEncoder).encode(command.password());
        }

        @Test
        @DisplayName("중복된 이메일로 회원가입시 예외를 반환한다.")
        void givenDuplicatedEmailWhenRegisteringThenThrowException() {
            // Given
            UserRegisterCommand command = UserFixtures.registerUserCommand();

            String encryptedPassword = "encryptedPassword";
            User savedUser = UserFixtures.user(command.name(), command.email(), encryptedPassword);

            when(bCryptPasswordEncoder.encode(command.password())).thenReturn(encryptedPassword);
            when(userRegisterPort.existsByEmail(any(String.class))).thenReturn(true);

            // When Then
            assertThatThrownBy(() -> {
                sut.register(command);
            })
                .isInstanceOf(DuplicateEmailException.class);
        }
    }
}