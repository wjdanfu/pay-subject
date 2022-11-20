package com.example.pay.dto;


import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PayReqDto {

    @NotEmpty(message = "카드 넘버입력")
    private Long cardNumber;
    private int expireDate;
    private int cvc;
    private int installment;
    private int payment;
    private int vat;
}
