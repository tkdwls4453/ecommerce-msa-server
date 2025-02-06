package com.msa.order.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msa.order.adapter.in.web.dto.CreateNewOrderRequest;
import com.msa.order.application.port.in.CreateNewOrderCommand;
import com.msa.order.application.port.in.CreateNewOrderUseCase;
import com.msa.order.application.port.out.ApplyCouponUseCase;
import com.msa.order.application.port.out.DecreaseStockUseCase;
import com.msa.order.application.port.out.OrderCreatePort;
import com.msa.order.domain.Order;
import com.msa.order.domain.OrderFixtures;
import com.msa.order.domain.OrderStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateNewOrderUseCase createNewOrderUseCase;

    @MockitoBean
    private ApplyCouponUseCase applyCouponUseCase;

    @MockitoBean
    private DecreaseStockUseCase decreaseStockUseCase;

    @MockitoBean
    private OrderCreatePort orderCreatePort;

    @Nested
    @DisplayName("POST /order/new")
    class AcceptOrder{

        @Test
        @DisplayName("정상 데이터로 주문 접수시 200 OK 를 반환한다.")
        void testOrderAcceptanceReturnsOk() throws Exception {
            // Given
            CreateNewOrderRequest request = OrderFixtures.newOrderWithFixedCouponRequest();
            LocalDateTime orderTime = LocalDateTime.now();
            Order createdOrder = OrderFixtures.order(1L, OrderStatus.PAYMENT_PENDING, orderTime);

            String body = objectMapper.writeValueAsString(request);
            when(createNewOrderUseCase.createNewOrder(anyLong(), any(CreateNewOrderCommand.class)))
                .thenReturn(createdOrder);

            // When Then
            mockMvc.perform(
                    post("/order/new")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("SUCCESS"))
                .andExpect(jsonPath("$.code").value("S200"))
                .andExpect(jsonPath("$.data.orderLine").isNotEmpty())
                .andExpect(jsonPath("$.data.orderId").value(1L))
                .andExpect(jsonPath("$.data.orderStatus").value("PAYMENT_PENDING"));

            verify(createNewOrderUseCase, times(1))
                .createNewOrder(anyLong(), any(CreateNewOrderCommand.class));
        }

        @Test
        @DisplayName("배송 정보 없이 주문 접수시 400 BAD_REQUEST 를 반환한다.")
        void testOrderAcceptanceWithOutShippingAddressReturnsBadRequest() throws Exception {
            // Given
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .shippingInfo(null)
                .orderLine(OrderFixtures.orderLine())
                .coupon(OrderFixtures.fixedCoupon())
                .totalAmount(OrderFixtures.FIXED_TOTAL_AMOUNT)
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then
            mockMvc.perform(
                    post("/order/new")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"))
                .andExpect(jsonPath("$.errors.shippingInfo").value("배송 정보는 필수입니다."));
        }

        @Test
        @DisplayName("주문 아이템 없이 주문 접수시 400 BAD_REQUEST 를 반환한다.")
        void testOrderAcceptanceWithOutOrderLineReturnsBadRequest() throws Exception {
            // Given
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .shippingInfo(OrderFixtures.shippingInfo())
                .orderLine(null)
                .coupon(OrderFixtures.fixedCoupon())
                .totalAmount(OrderFixtures.PERCENT_TOTAL_AMOUNT)
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then
            mockMvc.perform(
                    post("/order/new")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"))
                .andExpect(jsonPath("$.errors.orderLine").value("주문 아이템 정보는 필수입니다."));
        }

        @Test
        @DisplayName("빈 주문 아이템 정보로 주문 접수시 400 BAD_REQUEST 를 반환한다.")
        void testOrderAcceptanceNoOrderItemReturnsBadRequest() throws Exception {
            // Given
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .shippingInfo(OrderFixtures.shippingInfo())
                .orderLine(new ArrayList<>())
                .coupon(OrderFixtures.fixedCoupon())
                .totalAmount(OrderFixtures.FIXED_TOTAL_AMOUNT)
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then
            mockMvc.perform(
                    post("/order/new")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"))
                .andExpect(jsonPath("$.errors.orderLine").value("최소 1개 이상의 아이템을 주문해야 합니다."));
        }

        @Test
        @DisplayName("총 주문 금액이 음수인 주문 접수시 400 BAD_REQUEST 를 반환한다.")
        void testOrderAcceptanceWithNegativeTotalAmountReturnsBadRequest() throws Exception {
            // Given
            CreateNewOrderRequest request = CreateNewOrderRequest.builder()
                .shippingInfo(OrderFixtures.shippingInfo())
                .orderLine(OrderFixtures.orderLine())
                .coupon(OrderFixtures.fixedCoupon())
                .totalAmount(-1)
                .build();

            String body = objectMapper.writeValueAsString(request);

            // When Then
            mockMvc.perform(
                    post("/order/new")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("FAIL"))
                .andExpect(jsonPath("$.code").value("F400"))
                .andExpect(jsonPath("$.errors.totalAmount").value("주문 금액은 0 이상입니다."));
        }
    }
}