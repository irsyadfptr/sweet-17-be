package com.sweet17.qrgenerator;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tbl_trx;")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    String fileName;
    Date createdDate;
    Date expiredDate;
    String link;

}