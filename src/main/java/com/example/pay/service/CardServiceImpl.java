package com.example.pay.service;


import com.example.pay.dto.PayReqDto;
import com.example.pay.dto.PayResDto;
import com.example.pay.entity.Card;
import com.example.pay.entity.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    @Transactional
    public PayResDto pay(PayReqDto payReqDto) {

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);


        PayResDto payResDto = new PayResDto();
        String data = dataPart(payReqDto);



        String header = headerPart(data);

        payResDto.setCardStringData(header+data);

        Card card = new Card(header+data);


        String id = String.format("%012d", cardRepository.currentSeqID());

        String unique_id = formatedNow + id;

        card.setUnique_id(unique_id);


        Card cardEntity =  cardRepository.save(card);

        payResDto.setManageCardNumber(String.valueOf(cardEntity.getUnique_id()));
        payResDto.setCardStringData(cardEntity.getCardStringData());



        return payResDto;
    }

    @Override
    public String headerPart(String data) {



        return "null";
    }

    @Override
    public String dataPart(PayReqDto payReqDto) {

        String cardNumber = String.format("%-20d", payReqDto.getCardNumber());

        String installment = String.format("%02d", payReqDto.getInstallment());

        String expireDate = String.format("%-4d",payReqDto.getExpireDate());

        String cvc = String.valueOf(payReqDto.getCvc());

        String payment = String.format("%10d",payReqDto.getPayment());

        String vat = String.format("%010d", payReqDto.getVat());

        String manageNumber = String.format("%-20s","");

        String encryptedCard = String.format("%-300s","");

        String spareField = String.format("%47s","");

        String cardStringData =
                cardNumber + installment + expireDate + cvc + payment + vat + manageNumber +encryptedCard+spareField;

        System.out.println(cardStringData.length());



        return cardStringData;
    }
}
