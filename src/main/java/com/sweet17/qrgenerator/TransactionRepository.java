package com.sweet17.qrgenerator;

import com.sweet17.qrgenerator.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository <Transaction, Long> {
    Optional<Transaction> findTransactionByLink (String link);
    Optional<Transaction> findByLink(String link);


}
