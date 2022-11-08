package com.example.pay.dto;


import com.example.pay.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayResDto {

    private String manageCardNumber;
    private String cardStringData;


    public Card toEntity(){
        return Card.builder()
                .cardStringData(cardStringData)
                .build();
    }
}
