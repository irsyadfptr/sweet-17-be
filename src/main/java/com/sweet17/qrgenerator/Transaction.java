package com.sweet17.qrgenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Transaction {
    Long transactionId;
    String fileName;
    Date createdDate;
    Date expiredDate;
    String link;
}