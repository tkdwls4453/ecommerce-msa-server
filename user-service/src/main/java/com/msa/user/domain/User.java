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
    private User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }


}
