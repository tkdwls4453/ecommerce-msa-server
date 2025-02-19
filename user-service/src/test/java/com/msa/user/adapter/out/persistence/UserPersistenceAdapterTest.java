package com.msa.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.user.domain.User;
import com.msa.user.domain.UserFixtures;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @InjectMocks
    private UserPersistenceAdapter sut;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Test
    @DisplayName("유저 도메인을 엔티티로 변경하여 저장한다.")
    void shouldChangeToEntityAndSave(){
        // Given
        User user = UserFixtures.user();
        UserEntity savedUserEntity = UserFixtures.userEntity(1L, user);

        when(userJpaRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

        // When
        User savedUser = sut.save(user);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo(1L);
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());

        verify(userJpaRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("존재하는 이메일로 유저 조회시, 엔티티를 도메인으로 변경하여 리턴한다.")
    void shouldConvertEntityToDomainWhenFetchingUser() {
        // Given
        User user = UserFixtures.user();
        UserEntity savedUserEntity = UserFixtures.userEntity(1L, user);

        when(userJpaRepository.findByEmail(user.getEmail())).thenReturn(
            Optional.ofNullable(savedUserEntity)
        );

        // When
        User savedUser = sut.findByEmail(user.getEmail()).orElse(null);

        // Then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUserId()).isEqualTo(1L);
        assertThat(savedUser.getName()).isEqualTo(user.getName());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());

        verify(userJpaRepository).findByEmail(user.getEmail());
    }

    @Test
    @DisplayName("존재하지 않은 이메일로 유저 조회시, Optional.empty 리턴한다.")
    void shouldConvertEntityToDomainWhenFetchingUser_ReturnEmpty() {
        // Given
        String nonExistsEmail = "nonExistsEmail";

        when(userJpaRepository.findByEmail(nonExistsEmail)).thenReturn(
            Optional.empty()
        );

        // When
        User savedUser = sut.findByEmail(nonExistsEmail).orElse(null);

        // Then
        assertThat(savedUser).isNull();
        verify(userJpaRepository).findByEmail(nonExistsEmail);
    }


    @Test
    @DisplayName("이메일이 존재할 때 이메일 존재를 확인하면 true 를 리턴한다.")
    void existsByEmail_returnTrue(){
        // Given
        String email = "test123@naver.com";

        when(userJpaRepository.existsByEmail(email)).thenReturn(true);

        // When
        Boolean result = sut.existsByEmail(email);
        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("이메일이 존재하지 않을 때 이메일 존재를 확인하면 false 를 리턴한다.")
    void existsByEmail_returnFalse(){
        // Given
        String nonExistsEmail = "nonexist@naver.com";

        when(userJpaRepository.existsByEmail(nonExistsEmail)).thenReturn(false);

        // When
        Boolean result = sut.existsByEmail(nonExistsEmail);
        // Then
        assertThat(result).isFalse();
    }
}