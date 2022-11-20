package com.example.pay.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Builder
    public PayReqDto(Long cardNumber,int expireDate, int cvc, int installment, int payment, int vat){
        this.cardNumber = cardNumber;
        this.expireDate = expireDate;
        this.installment= installment;
        this.payment = payment;
        this.vat=vat;
        this.cvc =cvc;
    }

}
