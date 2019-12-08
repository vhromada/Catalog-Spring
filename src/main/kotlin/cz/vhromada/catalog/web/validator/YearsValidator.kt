package cz.vhromada.catalog.web.validator

import cz.vhromada.catalog.web.fo.SeasonFO
import cz.vhromada.catalog.web.validator.constraints.Years
import cz.vhromada.common.utils.Constants
import java.util.regex.Pattern
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * A class represents validator for years constraint.
 *
 * @author Vladimir Hromada
 */
class YearsValidator : ConstraintValidator<Years, SeasonFO> {

    override fun isValid(value: SeasonFO?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (value == null) {
            return true
        }

        val startYear = value.startYear
        val endYear = value.endYear
        if (isNotStringValid(startYear) || isNotStringValid(endYear)) {
            return true
        }

        val startYearValue = Integer.parseInt(startYear!!)
        val endYearValue = Integer.parseInt(endYear!!)
        return if (isNotIntValid(startYearValue) || isNotIntValid(endYearValue)) {
            true
        } else startYearValue <= endYearValue
    }

    /**
     * Validates year as string.
     *
     * @param value value to validate
     * @return true if value isn't null and is valid integer
     */
    private fun isNotStringValid(value: String?): Boolean {
        return value == null || !PATTERN.matcher(value).matches()
    }

    /**
     * Validates year as integer.
     *
     * @param value value to validate
     * @return true if value is in valid range
     */
    private fun isNotIntValid(value: Int): Boolean {
        return value < Constants.MIN_YEAR || value > Constants.CURRENT_YEAR
    }

    @Suppress("CheckStyle")
    companion object {

        /**
         * Year pattern
         */
        private val PATTERN = Pattern.compile("\\d{4}")

    }

}
