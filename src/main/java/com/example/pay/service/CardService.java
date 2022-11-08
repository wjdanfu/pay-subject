package com.example.pay.service;


import com.example.pay.dto.PayReqDto;
import com.example.pay.dto.PayResDto;

public interface CardService {

    PayResDto pay(PayReqDto payReqDto);

    String headerPart(String data);
    String dataPart(PayReqDto payReqDto);
}
