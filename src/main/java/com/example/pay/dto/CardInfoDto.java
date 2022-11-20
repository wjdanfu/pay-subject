package com.example.pay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CardInfoDto {
    private String cardNumber;
    private String expireDate;
    private String cvc;
}
