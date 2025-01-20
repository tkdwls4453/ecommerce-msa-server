package com.msa.user.adapter.in.web;

import com.msa.common.response.ApiResponse;
import com.msa.user.adapter.in.web.dto.request.UserRegisterRequest;
import com.msa.user.application.port.in.UserRegisterCommand;
import com.msa.user.application.port.in.UserRegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRegisterController {
    private final UserRegisterUseCase userRegisterUseCase;

    @PostMapping("/auth/register")
    public ApiResponse<Void> register(
        @Valid @RequestBody UserRegisterRequest request
    ){
        userRegisterUseCase.register(UserRegisterCommand.builder()
            .name(request.name())
            .email(request.email())
            .password(request.password())
            .build());

        return ApiResponse.success();
    }
}
