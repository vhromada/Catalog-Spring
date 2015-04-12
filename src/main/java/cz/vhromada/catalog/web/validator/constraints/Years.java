package cz.vhromada.catalog.web.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cz.vhromada.catalog.web.validator.YearsValidator;

/**
 * An annotation represents years constraint.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = YearsValidator.class)
@Documented
public @interface Years {

    String message() default "{cz.vhromada.catalog.validator.Years.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
