package cz.vhromada.catalog.web.validator.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cz.vhromada.catalog.web.validator.LanguagesValidator;

/**
 * An annotation represents languages constraint.
 *
 * @author Vladimir Hromada
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LanguagesValidator.class)
@Documented
public @interface Languages {

    String message() default "{cz.vhromada.catalog.validator.Languages.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
