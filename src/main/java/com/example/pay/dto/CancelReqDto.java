package com.example.pay.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CancelReqDto {

    private String uniqueId;
    private int cancelPrice;
    private int vat;

    @Builder
    public CancelReqDto(String uniqueId, int cancelPrice, int vat){
        this.uniqueId = uniqueId;
        this.cancelPrice = cancelPrice;
        this.vat = vat;
    }
}
