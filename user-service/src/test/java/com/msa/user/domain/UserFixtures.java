package com.msa.user.domain;

import com.msa.user.application.port.in.UserRegisterCommand;

public class UserFixtures {
    public static User user(String name, String email, String encryptedPassword ){
        return User.builder()
            .name(name)
            .email(email)
            .password(encryptedPassword)
            .build();
    }

    public static UserRegisterCommand registerUserCommand() {
        return UserRegisterCommand.builder()
            .name("testName")
            .email("test123@naver.com")
            .password("testPassword")
            .build();
    }
}
