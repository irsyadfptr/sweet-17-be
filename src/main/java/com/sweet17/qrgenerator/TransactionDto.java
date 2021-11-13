package com.sweet17.qrgenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    Long transactionId;
    String fileName;
    String link;
//
//    Date createdDate;
//    Date expiredDate;
}
