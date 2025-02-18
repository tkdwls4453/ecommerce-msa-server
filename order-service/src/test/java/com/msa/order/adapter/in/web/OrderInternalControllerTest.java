package com.msa.order.adapter.in.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.msa.order.application.port.in.OrderQueryUseCase;
import com.msa.order.domain.Order;
import com.msa.order.domain.OrderFixtures;
import com.msa.order.domain.OrderStatus;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderInternalController.class)
class OrderInternalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderQueryUseCase orderQueryUseCase;

    @Nested
    @DisplayName("Get /internal/orders/{orderId}")
    class GetOrder{

        @Test
        @DisplayName("주문 아이디로 주문 조회시 요약 주문 정보와 200 OK 를 반환한다.")
        void test2000() throws Exception {
            // Given
            Long orderId = 1L;
            LocalDateTime orderTime = LocalDateTime.now();
            Order order = OrderFixtures.order(orderId, OrderStatus.PAYMENT_PENDING, orderTime);

            when(orderQueryUseCase.getOrderById(orderId)).thenReturn(order);

            // When Then
            mockMvc.perform(get("/internal/orders/{orderId}", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.code").value("S200"))
                .andExpect(jsonPath("$.data.orderId").value(order.getOrderId()))
                .andExpect(jsonPath("$.data.orderStatus").value(order.getOrderStatus().toString()))
                .andExpect(jsonPath("$.data.customerId").value(order.getCustomerId()))
                .andExpect(jsonPath("$.data.orderCode").value(order.getOrderCode().toString()))
            ;

            verify(orderQueryUseCase, times(1)).getOrderById(orderId);
        }
    }
}