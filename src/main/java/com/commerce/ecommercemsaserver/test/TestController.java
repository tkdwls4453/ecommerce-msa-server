package com.commerce.ecommercemsaserver.test;

import com.commerce.ecommercemsaserver.test.exception.Test1Exception;
import com.commerce.ecommercemsaserver.test.exception.Test2Exception;
import com.msa.common.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    // 커스텀 예외 테스트 1
    @GetMapping("/test1")
    public ApiResponse test1(){
        if(1 == 1) throw new Test1Exception();
        return ApiResponse.success();
    }
    // 커스텀 예외 테스트 2
    @GetMapping("/test2")
    public ApiResponse test2(){
        if(1 == 1) throw new Test2Exception();
        return ApiResponse.success();
    }

    // 요청 Validation 테스트
    @GetMapping("/test3")
    public ApiResponse test3(
        @RequestBody @Valid TestRequest request
    ){
        if(1 == 1) throw new Test2Exception();
        return ApiResponse.success();
    }

    // 예상치 못한 에러 테스트
    @GetMapping("/test4")
    public ApiResponse test4(){
        int a = 10 / 0;
        return ApiResponse.success();
    }
}
