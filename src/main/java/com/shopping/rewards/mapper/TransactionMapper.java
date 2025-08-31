package com.shopping.rewards.mapper;

import com.shopping.rewards.dto.TransactionDto;
import com.shopping.rewards.model.Transaction;

public class TransactionMapper {

	private TransactionMapper() {}

    public static TransactionDto toDto(Transaction transaction, long points) {
        if (transaction == null) {
            return null;
        }
        return new TransactionDto(  
        		transaction.getId(), 
        		transaction.getCustomerId(),
                transaction.getDate(),
                transaction.getAmount(),
                points
        );
    }

    public static Transaction toEntity(TransactionDto dto) {
        if (dto == null) {
            return null;
        }
        return new Transaction(
                dto.getCustomerId(),
                dto.getDate(),
                dto.getAmount() 
        );
    }
    
}
