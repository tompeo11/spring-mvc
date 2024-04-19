package com.tom.javaspring.validation;

import com.tom.javaspring.validation.impl.ValidEmailImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = ValidEmailImpl.class)
@Retention(RUNTIME)
@Target({TYPE, FIELD, ANNOTATION_TYPE})
public @interface ValidEmail {
    String message() default "Invalid email format";

    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
