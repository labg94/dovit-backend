package com.dovit.backend.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Ramón París
 * @since 06-08-2019
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RutValidator.class)
@Documented
public @interface Rut {

  String message() default "RUT in wrong format.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
