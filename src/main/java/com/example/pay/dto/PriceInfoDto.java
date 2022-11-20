package com.example.pay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PriceInfoDto {
    private int price;
    private int vat;
}
