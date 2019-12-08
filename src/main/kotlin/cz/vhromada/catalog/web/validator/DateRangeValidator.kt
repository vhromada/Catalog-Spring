package cz.vhromada.catalog.web.validator

import cz.vhromada.catalog.web.validator.constraints.DateRange
import cz.vhromada.common.utils.Constants
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * A class represents validator for date range constraint.
 *
 * @author Vladimir Hromada
 */
class DateRangeValidator : ConstraintValidator<DateRange, String> {

    /**
     * Minimal date
     */
    private var minDate: Int = 0

    override fun initialize(dateRange: DateRange) {
        minDate = dateRange.value
    }

    override fun isValid(date: String?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (date == null || !PATTERN.matcher(date).matches()) {
            return false
        }

        return Integer.parseInt(date) in minDate..Constants.CURRENT_YEAR
    }

    @Suppress("CheckStyle")
    companion object {

        /**
         * Date pattern
         */
        private val PATTERN = Pattern.compile("\\d{4}")

    }

}
