package cz.vhromada.catalog.web.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cz.vhromada.catalog.web.validator.TimeValidator;

/**
 * An annotation represents time constraint.
 *
 * @author Vladimir Hromada
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TimeValidator.class)
@Documented
public @interface Time {

    /**
     * Returns error message template.
     *
     * @return error message template
     */
    String message() default "{cz.vhromada.catalog.validator.Time.message}";

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
