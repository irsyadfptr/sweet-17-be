package com.sweet17.qrgenerator;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransactionEntity(TransactionDto transactionDto);


    @Mapping(target = "link", expression = "java(generateLink(10))")
    TransactionDto toTransactionDto(Transaction transaction);

    default String generateLink(Integer n){
        String AlphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "0123456789" + "abcdefghijklmnopqrstuvwxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++){
            int index = (int) (AlphaNumeric.length() * Math.random());
            sb.append(AlphaNumeric.charAt(index));
        }
        return sb.toString();
    }

}
