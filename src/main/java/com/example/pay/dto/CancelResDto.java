package com.example.pay.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CancelResDto {
    private String uniqueId;
    private String cardStringData;
}
