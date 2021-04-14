package com.campaign.owner.campaignowner.constraint;

import com.campaign.owner.campaignowner.constraint.validator.EnumValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumValidatorImpl.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@NotNull(message = "State cannot be null")
@ReportAsSingleViolation
public @interface EnumValidator {
    String[] acceptedValues();

    Class<? extends Enum<?>> enumClass();

    String message() default "State is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
