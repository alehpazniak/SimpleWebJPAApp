package com.mastery.java.task.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {
    String message() default "Employee must be adult";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
