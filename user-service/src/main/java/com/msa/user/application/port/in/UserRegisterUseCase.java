package com.msa.user.application.port.in;

import com.msa.user.domain.User;

public interface UserRegisterUseCase {

    User register(UserRegisterCommand userRegisterCommand);
}
