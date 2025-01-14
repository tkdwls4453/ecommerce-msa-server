package com.msa.user.application.service;

import com.msa.user.application.port.in.UserRegisterCommand;
import com.msa.user.application.port.in.UserRegisterUseCase;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserRegisterUseCase {

    @Override
    public void register(UserRegisterCommand userRegisterCommand) {
    }
}
