package com.msa.user.domain;

import com.msa.user.adapter.out.persistence.UserEntity;
import com.msa.user.application.port.in.UserRegisterCommand;

public class UserFixtures {
    public static User user(String name, String email, String encryptedPassword ){
        return User.builder()
            .name(name)
            .email(email)
            .password(encryptedPassword)
            .build();
    }

    public static User user(){
        return User.builder()
            .name("testName")
            .email("test123@naver.com")
            .password("testPassword")
            .build();
    }

    public static UserRegisterCommand registerUserCommand() {
        return UserRegisterCommand.builder()
            .name("testName")
            .email("test123@naver.com")
            .password("testPassword")
            .build();
    }

    public static UserEntity userEntity(Long id, User user) {
        return UserEntity.builder()
            .id(id)
            .name(user.getName())
            .email(user.getEmail())
            .password(user.getPassword())
            .build();
    }
}
