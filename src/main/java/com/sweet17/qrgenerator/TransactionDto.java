package com.sweet17.qrgenerator;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    Long transactionId;
    String link;
    Date createdDate;
    Date expiredDate;

}


