package com.shopping.rewards.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.shopping.rewards.dto.RewardRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateRangeValidator implements ConstraintValidator<DateRange, RewardRequest> {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    @Override
    public void initialize(DateRange constraintAnnotation) { 
    }
    
    @Override
    public boolean isValid(RewardRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getFrom() == null || request.getTo() == null) {
            return true;  
        }
        
        try {
            LocalDate fromDate = LocalDate.parse(request.getFrom(), FORMATTER);
            LocalDate toDate = LocalDate.parse(request.getTo(), FORMATTER);
            
            return !fromDate.isAfter(toDate);
        } catch (DateTimeParseException e) {
            return true;
        }
    }
}
