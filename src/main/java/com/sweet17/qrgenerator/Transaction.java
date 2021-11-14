package com.sweet17.qrgenerator;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "sweet_tjh")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    String link;
    Date createdDate;
    Date expiredDate;

}