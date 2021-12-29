package com.mastery.java.task.postProcessor;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@Component
public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public void initialize(Adult constraintAnnotation) {
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {

        return value != null && value.isBefore(LocalDate.now().minusYears(18));
    }
}
