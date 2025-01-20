package com.msa.user.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
    private Long userId;
    private String name;
    private String password;
    private String email;

    @Builder
    private User(Long userId, String name, String password, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
