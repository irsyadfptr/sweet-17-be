package com.sweet17.qrgenerator;

import com.sweet17.qrgenerator.dto.TransactionDto;
import com.sweet17.qrgenerator.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransactionEntity(TransactionDto transactionDto);


    @Mapping(target = "link", expression = "java(generateLink(10))")
    TransactionDto toTransactionDto(Transaction transaction);

    default String generateLink(int n){
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
