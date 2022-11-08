package com.example.pay.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CancelReqDto {

    private String uniqueId;
    private int cancelPrice;
    private int vat;
}
