package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.catalog.web.validator.constraints.Time;

import org.springframework.util.StringUtils;

/**
 * A class represents show validator for time constraint.
 *
 * @author Vladimir Hromada
 */
public class TimeValidator implements ConstraintValidator<Time, TimeFO> {

    @Override
    public void initialize(final Time time) {
    }

    @Override
    public boolean isValid(final TimeFO time, final ConstraintValidatorContext constraintValidatorContext) {
        if (time == null) {
            return false;
        }
        if (StringUtils.isEmpty(time.getHours()) || StringUtils.isEmpty(time.getMinutes()) || StringUtils.isEmpty(time.getSeconds())) {
            return true;
        }

        final int length = Integer.parseInt(time.getHours()) + Integer.parseInt(time.getMinutes()) + Integer.parseInt(time.getSeconds());
        return length > 0;
    }

}
