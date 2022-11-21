package com.example.pay.service;


import com.example.pay.dto.*;
import com.example.pay.entity.Card;
import com.example.pay.entity.CardRepository;
import com.example.pay.util.AES256;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@RequiredArgsConstructor
@Service
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    @Override
    public SearchResDto search(String uniqueId) throws Exception {
        Card paycard  = cardRepository.findByUniqueId(uniqueId)
                .orElseThrow(() -> new NotFoundException("없는 결제 입니다."));

        String cardStringData = paycard.getCardStringData();

        SearchResDto searchResDto = new SearchResDto();

        CardInfoDto cardInfoDto = new CardInfoDto();

        AES256 aes256 = new AES256();
        String cardInfo[] = aes256.decrypt(cardStringData.substring(103,403).trim()).split("/");
        String cardNumber = cardInfo[0];
        String expireDate = cardInfo[1];
        String cvc = cardInfo[2];

        cardNumber = cardNumber.replaceAll("(?<=.{6}).", "*");

        cardInfoDto.setCardNumber(cardNumber);
        cardInfoDto.setExpireDate(expireDate);
        cardInfoDto.setCvc(cvc);

        searchResDto.setCardInfo(cardInfoDto);

        searchResDto.setUniqueId(paycard.getUniqueId());

        if (cardStringData.substring(4, 14).trim().equals("PAYMENT")){
            searchResDto.setPayOrCancel("결제");
        }else {
            searchResDto.setPayOrCancel("취소");
        }

        PriceInfoDto priceInfoDto = new PriceInfoDto();
        priceInfoDto.setPrice(Integer.parseInt(cardStringData.substring(63,73).trim()));
        priceInfoDto.setVat(Integer.parseInt(cardStringData.substring(73,83).trim()));

        searchResDto.setPriceInfoDto(priceInfoDto);

        return searchResDto;
    }

    @Override
    @Transactional
    public PayResDto pay(PayReqDto payReqDto) throws Exception {

        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);


        PayResDto payResDto = new PayResDto();
        String data = payDataPart(payReqDto);


        String id = String.format("%012d", cardRepository.currentSeqID());

        String unique_id = formatedNow + id;

        String header = headerPart(data, unique_id);

        payResDto.setCardStringData(header+data);

        Card card = new Card(header+data, unique_id);



        Card cardEntity =  cardRepository.save(card);

        payResDto.setManageCardNumber(String.valueOf(cardEntity.getUniqueId()));
        payResDto.setCardStringData(cardEntity.getCardStringData());



        return payResDto;
    }

    @Override
    public String headerPart(String data, String unique_id) {



        String state = String.format("%-10s", "PAYMENT");


        String manageNum = String.format("%-20s", unique_id);


        String dataSize = String.format("%4d", data.length()+manageNum.length()+state.length());


        String header = dataSize + state + manageNum;


        return header;
    }

    @Override
    public String payDataPart(PayReqDto payReqDto) throws Exception {

        String cardNumber = String.format("%-20d", payReqDto.getCardNumber());

        String installment = String.format("%02d", payReqDto.getInstallment());

        String expireDate = String.format("%-4d",payReqDto.getExpireDate());

        String cvc = String.valueOf(payReqDto.getCvc());

        String payment = String.format("%10d",payReqDto.getPayment());

        String vat = String.format("%010d", payReqDto.getVat());


        String manageNumber = String.format("%-20s","");

        AES256 aes256 = new AES256();

        String encryptedCard = String.format("%-300s",aes256.encrypt(payReqDto.getCardNumber()
                +"/"+
                payReqDto.getExpireDate()
                +"/"+
                payReqDto.getCvc()));

        String spareField = String.format("%47s","");


        String cardStringData =
                cardNumber + installment + expireDate + cvc + payment + vat + manageNumber +encryptedCard+spareField;

        System.out.println(cardStringData.length());



        return cardStringData;
    }

    @Override
    @Transactional
    public CancelResDto cancel(CancelReqDto cancelReqDto) throws NotFoundException{
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedNow = now.format(formatter);


        Card paycard  = cardRepository.findByUniqueId(cancelReqDto.getUniqueId())
                .orElseThrow(() -> new NotFoundException("없는 결제 입니다."));

        String cardStringData = paycard.getCardStringData();

        int price = Integer.parseInt(cardStringData.substring(63,73).trim());

        if (price < cancelReqDto.getCancelPrice()){
            throw new NotFoundException("결제금액보다 작습니다.");
        }

        String id = String.format("%012d", cardRepository.currentSeqID());

        String unique_id = formattedNow + id;

        CancelResDto cancelResDto = new CancelResDto();

        String data = cancelDataPart(cardStringData, unique_id, String.format("%10d",price-cancelReqDto.getCancelPrice()));



        Card card = new Card(data,unique_id);
        Card cardEntity =  cardRepository.save(card);


        cancelResDto.setCardStringData(cardEntity.getCardStringData());
        cancelResDto.setUniqueId(cardEntity.getUniqueId());

        return cancelResDto;
    }


    @Override
    public String cancelDataPart(String stringData, String unique_id, String price) {

        String payManageNumber = stringData.substring(14,34);

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(stringData);

        stringBuffer.replace(4,14,"CANCEL    ");

        stringBuffer.replace(14,34,unique_id);

        stringBuffer.replace(63,73,price);


        stringBuffer.replace(83,103,payManageNumber);

        return stringBuffer.toString();
    }
}
