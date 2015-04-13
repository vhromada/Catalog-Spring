package cz.vhromada.catalog.web.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cz.vhromada.catalog.web.validator.ImdbMovieValidator;
import cz.vhromada.catalog.web.validator.ImdbShowValidator;

/**
 * An annotation represents IMDB code constraint.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ImdbMovieValidator.class, ImdbShowValidator.class })
@Documented
public @interface Imdb {

    String message() default "{cz.vhromada.catalog.validator.Imdb.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
