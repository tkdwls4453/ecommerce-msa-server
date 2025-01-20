package com.msa.user.application.service;

import com.msa.user.application.port.in.UserRegisterCommand;
import com.msa.user.application.port.in.UserRegisterUseCase;
import com.msa.user.application.port.out.UserRegisterPort;
import com.msa.user.domain.User;
import com.msa.user.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAuthService implements UserRegisterUseCase {
    private final UserRegisterPort userRegisterPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User register(UserRegisterCommand userRegisterCommand) {
        String encodedPassword = bCryptPasswordEncoder.encode(userRegisterCommand.password());

        if(userRegisterPort.existsByEmail(userRegisterCommand.email())) {
            throw new DuplicateEmailException();
        }

        User user = User.builder()
            .name(userRegisterCommand.name())
            .password(encodedPassword)
            .email(userRegisterCommand.email())
            .build();

        return userRegisterPort.save(user);
    }
}
