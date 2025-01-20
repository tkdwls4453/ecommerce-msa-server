package com.msa.user.adapter.out.persistence;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.user.domain.User;
import com.msa.user.domain.UserFixtures;
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
}