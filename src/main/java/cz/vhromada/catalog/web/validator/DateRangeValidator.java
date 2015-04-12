package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.commons.Constants;
import cz.vhromada.catalog.web.validator.constraints.DateRange;

/**
 * A class represents validator for date range constraint.
 *
 * @author Vladimir Hromada
 */
public class DateRangeValidator implements ConstraintValidator<DateRange, String> {

    /**
     * Minimal date
     */
    private int minDate;

    /**
     * Maximal date
     */
    private int maxDate;

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public void initialize(final DateRange dateRange) {
        minDate = dateRange.value();
        maxDate = Constants.CURRENT_YEAR;
    }

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public boolean isValid(final String date, final ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            return false;
        }
        if (!date.matches("\\d{4}")) {
            return false;
        }

        final int intValue = Integer.parseInt(date);
        return intValue >= minDate && intValue <= maxDate;
    }

}
