package com.example.pay.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<Card,Long> {

    @Query(value = "call next value for CARD_SEQ", nativeQuery = true)
    Long currentSeqID();
}
