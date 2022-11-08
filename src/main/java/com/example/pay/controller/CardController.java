package com.example.pay.controller;


import com.example.pay.dto.PayReqDto;
import com.example.pay.dto.PayResDto;
import com.example.pay.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {

        this.cardService = cardService;
    }

    @PostMapping("/card/pay")
    public PayResDto pay(@RequestBody PayReqDto payReqDto){

        PayResDto payResDto = cardService.pay(payReqDto);

        return payResDto;
    }

//    @PostMapping("/card/cancel")
//    public PayResDto cancel(@RequestBody PayReqDto payReqDto){
//
//        PayResDto payResDto = cardService.pay(payReqDto);
//
//        return payResDto;
//    }

}
