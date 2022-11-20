package com.example.pay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchResDto {
    private String uniqueId;
    private CardInfoDto cardInfo;
    private String payOrCancel;
    private PriceInfoDto priceInfoDto;
}
