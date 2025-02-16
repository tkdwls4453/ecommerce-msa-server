package com.msa.user.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.msa.user.application.port.out.UserReadPort;
import com.msa.user.domain.User;
import com.msa.user.domain.UserFixtures;
import com.msa.user.exception.EmailNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService sut;

    @Mock
    private UserReadPort userReadPort;

    @Test
    @DisplayName("이메일이 존재할 때 loadUserByUsername 을 호출하면 UserDetails 를 반환한다.")
    void loadUserByUsername_Success(){
        // Given
        User mockUser = UserFixtures.user();
        String email = mockUser.getEmail();

        when(userReadPort.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // When
        UserDetails userDetails = sut.loadUserByUsername(email);

        // Then
        assertNotNull(userDetails);
        assertEquals(email, userDetails.getUsername());
        assertEquals(mockUser.getPassword(), userDetails.getPassword());
        verify(userReadPort, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("이메일이 존재하지 않을 때, loadUserByUsername 을 호출하면 EmailNotfoundException 을 던진다.")
    void loadUserByUsername_EmailNotFound(){
        // Given
        String nonExistEmail = "nonexist@example.com";

        when(userReadPort.findByEmail(nonExistEmail)).thenReturn(Optional.empty());

        // When Then
        assertThrows(EmailNotFoundException.class, () -> sut.loadUserByUsername(nonExistEmail));
        verify(userReadPort, times(1)).findByEmail(nonExistEmail);
    }

    @Test
    @DisplayName("이메일이 존재할 때, getUserByEmail 을 호출하면 User 객체를 반환한다.")
    void getUserByEmail_Success(){
        // Given
        User mockUser = UserFixtures.user();
        String email = mockUser.getEmail();

        when(userReadPort.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // When
        User user = sut.getUserByEmail(email);

        // Then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(mockUser.getPassword(), user.getPassword());
        verify(userReadPort, times(1)).findByEmail(email);
    }

    @Test
    @DisplayName("이메일이 존재하지 않을 때, getUserByEmail 을 호출하면 EmailNotFoundException 을 던진다.")
    void getUserByEmail_EmailNotFound(){
        // Given
        String nonExistEmail = "nonexist@example.com";

        when(userReadPort.findByEmail(nonExistEmail)).thenReturn(Optional.empty());

        // When Then
        assertThrows(EmailNotFoundException.class, () -> sut.getUserByEmail(
            nonExistEmail
        ));
        verify(userReadPort, times(1)).findByEmail(nonExistEmail);
    }
}