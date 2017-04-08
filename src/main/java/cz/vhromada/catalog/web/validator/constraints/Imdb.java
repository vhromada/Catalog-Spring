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
 *
 * @author Vladimir Hromada
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { ImdbMovieValidator.class, ImdbShowValidator.class })
@Documented
public @interface Imdb {

    /**
     * Returns error message template.
     *
     * @return error message template
     */
    String message() default "{cz.vhromada.catalog.validator.Imdb.message}";

    /**
     * Returns groups constraint belongs to.
     *
     * @return groups constraint belongs to
     */
    Class<?>[] groups() default {};

    /**
     * Returns payload associated to constraint.
     *
     * @return payload associated to constraint
     */
    Class<? extends Payload>[] payload() default {};

}
