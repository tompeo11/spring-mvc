package com.tom.javaspring.validation.impl;

import com.tom.javaspring.validation.FieldMatch;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchImpl implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean result = true;

        try {
            Object firstObject = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            Object secondObject = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            result = firstObject == null && secondObject == null
                    || firstObject != null && firstObject.equals(secondObject);
        }catch (BeansException e) {
            throw new RuntimeException(e);
        }

        if (!result) {
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return result;
    }
}
