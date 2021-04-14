package com.campaign.owner.campaignowner.constraint.validator;

import com.campaign.owner.campaignowner.constraint.EnumValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;


public class EnumValidatorImpl implements ConstraintValidator<EnumValidator, String> {

    List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(EnumValidator constraintAnnotation) {
        valueList = of(constraintAnnotation.enumClass()
                .getEnumConstants()).map(e->e.toString()).collect(toList());
    }


}
