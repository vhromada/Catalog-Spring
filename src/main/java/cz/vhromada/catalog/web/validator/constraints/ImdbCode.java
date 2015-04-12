package cz.vhromada.catalog.web.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cz.vhromada.catalog.web.validator.ImdbCodeValidator;

/**
 * An annotation represents IMDB code constraint.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImdbCodeValidator.class)
@Documented
public @interface ImdbCode {

    String message() default "{cz.vhromada.catalog.validator.ImdbCode.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
