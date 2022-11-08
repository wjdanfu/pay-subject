package com.example.pay.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Table(name = "cards")
@Entity
@Getter
@SequenceGenerator(
        name = "CARD_SEQ_GENERATOR",
        sequenceName = "CARD_SEQ" //매핑할 데이터베이스 시퀀스 이름
        , initialValue = 1
        , allocationSize = 1
        )
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "CARD_SEQ_GENERATOR")
    private Long id;

    @Column
    private String unique_id;

    @Column(nullable = false, length = 450)
    private String cardStringData;

    @Builder
    public Card(String cardStringData){
        this.cardStringData = cardStringData;
    }

    public void setUnique_id(String unique_id){
        this.unique_id = unique_id;
    }

}
