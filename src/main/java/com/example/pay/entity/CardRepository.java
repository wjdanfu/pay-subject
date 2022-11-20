package com.example.pay.entity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface CardRepository extends CrudRepository<Card,Long> {

    @Query(value = "call next value for CARD_SEQ", nativeQuery = true)
    Long currentSeqID();

    Optional<Card> findByUniqueId(String unique_id);
//    Card findByUnique_id(String unique_id);

}
