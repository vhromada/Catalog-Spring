package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.utils.Constants;
import cz.vhromada.catalog.web.fo.SeasonFO;
import cz.vhromada.catalog.web.validator.constraints.Years;

/**
 * A class represents validator for years constraint.
 *
 * @author Vladimir Hromada
 */
public class YearsValidator implements ConstraintValidator<Years, SeasonFO> {

    /**
     * Minimal date
     */
    private int minDate;

    /**
     * Maximal date
     */
    private int maxDate;

    @Override
    public void initialize(final Years years) {
        minDate = Constants.MIN_YEAR;
        maxDate = Constants.CURRENT_YEAR;
    }

    @Override
    public boolean isValid(final SeasonFO value, final ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        final String startYear = value.getStartYear();
        final String endYear = value.getEndYear();
        if (!isStringValid(startYear) || !isStringValid(endYear)) {
            return true;
        }

        final int startYearValue = Integer.parseInt(startYear);
        final int endYearValue = Integer.parseInt(endYear);
        if (!isIntValid(startYearValue) || !isIntValid(endYearValue)) {
            return true;
        }

        return startYearValue <= endYearValue;
    }

    /**
     * Validates year as string.
     *
     * @param value value to validate
     * @return true if value isn't null and is valid integer
     */
    public static boolean isStringValid(final String value) {
        return value != null && value.matches("\\d{4}");
    }

    /**
     * Validates year as integer.
     *
     * @param value value to validate
     * @return true if value is in valid range
     */
    public boolean isIntValid(final int value) {
        return value >= minDate && value <= maxDate;
    }

}
