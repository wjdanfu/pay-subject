package com.example.pay.service;


import com.example.pay.dto.*;
import javassist.NotFoundException;

public interface CardService {

    PayResDto pay(PayReqDto payReqDto) throws Exception;

    String headerPart(String data, String unique_id);
    String payDataPart(PayReqDto payReqDto) throws Exception;

    String cancelDataPart(String stringData, String unique_id);

    CancelResDto cancel(CancelReqDto cancelReqDto) throws NotFoundException;

    SearchResDto search(String uniqueId) throws Exception;
}
