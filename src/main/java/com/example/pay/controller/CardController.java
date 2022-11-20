package com.example.pay.controller;


import com.example.pay.dto.*;
import com.example.pay.service.CardService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {

    private CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card/pay")
    public PayResDto pay(@RequestBody PayReqDto payReqDto) throws Exception {

        PayResDto payResDto = cardService.pay(payReqDto);

        return payResDto;
    }

    @PostMapping("/card/cancel")
    public CancelResDto cancel(@RequestBody CancelReqDto cancelReqDto) throws NotFoundException {

        CancelResDto cancelResDto = cardService.cancel(cancelReqDto);

        return cancelResDto;
    }

    @GetMapping("/card/{uniqueId}")
    public SearchResDto search(@PathVariable String uniqueId) throws Exception {

        SearchResDto searchResDto = cardService.search(uniqueId);

        return searchResDto;
    }

}
