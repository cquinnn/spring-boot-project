package com.webapp.identity.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.nimbusds.jose.Payload;

import jakarta.validation.Constraint;

@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = {DobValidator.class})
public @interface DobConstraint {
	String message() default "Invalid date of birth";
	int min() ;
	Class<?>[] groups() default { };
	Class<? extends Payload>[] payload() default { };
	
	
}