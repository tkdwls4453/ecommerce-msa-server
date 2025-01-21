package com.msa.user.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.user.adapter.in.web.dto.request.UserRegisterRequest;
import com.msa.user.application.port.in.UserRegisterCommand;
import com.msa.user.application.port.in.UserRegisterUseCase;
import com.msa.user.application.port.out.UserReadPort;
import com.msa.user.application.port.out.UserRegisterPort;
import com.msa.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserRegisterController.class)
class UserRegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserRegisterUseCase userRegisterUseCase;

    @MockitoBean
    private UserRegisterPort userRegisterPort;

    @MockitoBean
    private UserReadPort userReadPort;

    @Nested
    @DisplayName("POST /auth/register")
    class Register{
        private final String name = "testname";
        private final String email = "test123@naver.com";
        private final String password = "testpassword123";

        @Test
        @DisplayName("회원 가입 정보로 유저 정보를 생성한다. 200 OK")
        void testRegister_200OK() throws Exception {
            // Given
            UserRegisterRequest request = UserRegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

            String body = objectMapper.writeValueAsString(request);

            when(userRegisterUseCase.register(any(UserRegisterCommand.class)))
                .thenReturn(any(User.class));

            // When Then
            mockMvc.perform(
                    post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .with(csrf())
                )
                .andDo(print())
                .andExpect(
                    status().isOk()
                );

            verify(userRegisterUseCase, times(1))
                .register(any(UserRegisterCommand.class));
        }

        @ParameterizedTest
        @CsvSource({
            "'',test123@naver.com, testpassword123", // 이름 null
            "a,test123@naver.com, testpassword123", // 이름 1글자
            "testname, '', testpassword123", // 이메일 null
            "testname, test123, testpassword123", // 이메일 형식 X
            "testname, test123@naver.com, ''", // 비밀번호 null
            "testname, test123@naver.com, 1234", // 비밀번호 길이 8 미만
        })
        @DisplayName("유효하지 않은 정보로 가입 시도 시 실패한다. 400 BAD_REQUEST")
        void testRegister_400_BAD_REQUEST(String name, String email, String password) throws Exception {
            // Given
            UserRegisterRequest request = UserRegisterRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then
            mockMvc.perform(
                    post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"));


            verify(userRegisterUseCase, times(0))
                .register(any(UserRegisterCommand.class));
        }
    }

}