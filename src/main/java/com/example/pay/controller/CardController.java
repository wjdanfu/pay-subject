package com.example.pay.controller;


import com.example.pay.ApiResponse;
import com.example.pay.dto.*;
import com.example.pay.service.CardService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class CardController {

    private CardService cardService;

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandle(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/card/pay")
    public ApiResponse<PayResDto> pay(@RequestBody PayReqDto payReqDto) throws Exception {

        PayResDto payResDto = cardService.pay(payReqDto);

        return ApiResponse.ok(payResDto);
    }

    @PostMapping("/card/cancel")
    public ApiResponse<CancelResDto> cancel(@RequestBody CancelReqDto cancelReqDto) throws NotFoundException {

        CancelResDto cancelResDto = cardService.cancel(cancelReqDto);

        return ApiResponse.ok(cancelResDto);
    }

    @GetMapping("/card/{uniqueId}")
    public ApiResponse<SearchResDto> search(@PathVariable String uniqueId) throws Exception {

        SearchResDto searchResDto = cardService.search(uniqueId);

        return ApiResponse.ok(searchResDto);
    }

}
