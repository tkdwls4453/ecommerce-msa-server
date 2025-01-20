package com.msa.user.application.port.in;

import lombok.Builder;

@Builder
public record UserRegisterCommand(
    String name,
    String email,
    String password
) {

}
