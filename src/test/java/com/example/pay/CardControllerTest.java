package com.example.pay;

import com.example.pay.dto.CancelReqDto;
import com.example.pay.dto.PayReqDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
public class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void 결제() throws Exception{
        PayReqDto payReqDto = PayReqDto.builder()
                .cardNumber(1234568901L)
                .expireDate(1201)
                .cvc(123)
                .payment(1000)
                .vat(10)
                .installment(12)
                .build();



        this.mockMvc.perform(
                post("/card/pay")
                        .contentType(MediaType.APPLICATION_JSON) // Json 타입으로 지정
                        .content(objectMapper.writeValueAsString(payReqDto)))
                        .andExpect(status().isOk())
                        .andDo(print());
    }

    @Test
    public void 취소() throws Exception{

        PayReqDto payReqDto = PayReqDto.builder()
                .cardNumber(1234568901L)
                .expireDate(1201)
                .cvc(123)
                .payment(1000)
                .vat(10)
                .installment(12)
                .build();



        this.mockMvc.perform(
                        post("/card/pay")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(payReqDto)))
                .andExpect(status().isOk())
                .andDo(print());


        CancelReqDto cancelReqDto = CancelReqDto.builder()

                .cancelPrice(1000)
                .uniqueId("20221120000000000001")
                .vat(10).build();

        this.mockMvc.perform(
                        post("/card/cancel") //해당 url로 요청을 한다.
                                .contentType(MediaType.APPLICATION_JSON) // Json 타입으로 지정
                                .content(objectMapper.writeValueAsString(cancelReqDto)))
                .andExpect(status().isOk()) // 응답 status를 ok로 테스트
                .andDo(print()); // 응답값 print
    }

    @Test
    public void 조회() throws Exception{

        PayReqDto payReqDto = PayReqDto.builder()
                .cardNumber(1234568901L)
                .expireDate(1201)
                .cvc(123)
                .payment(1000)
                .vat(10)
                .installment(12)
                .build();



        this.mockMvc.perform(
                        post("/card/pay")
                                .contentType(MediaType.APPLICATION_JSON) // Json 타입으로 지정
                                .content(objectMapper.writeValueAsString(payReqDto)))
                .andExpect(status().isOk())
                .andDo(print());



        mockMvc.perform(get("/card/20221120000000000001"))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
