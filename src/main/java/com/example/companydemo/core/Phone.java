package com.example.companydemo.core;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * The annotated element must be a valid {@link Phone} value.
 * <p/>
 * Supported types are:
 * <ul>
 * <li>{@code java.lang.String}</li>
 * </ul>
 * <p>
 * {@code null} elements are considered valid.
 *
 * @author Sasa Bolic
 */
@Constraint(validatedBy = {PhoneValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface Phone {
 
     
    String message() default "";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
}