package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.commons.Constants;
import cz.vhromada.catalog.web.validator.constraints.ImdbCode;

/**
 * A class represents validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
public class ImdbCodeValidator implements ConstraintValidator<ImdbCode, String> {

    /**
     * Minimal IMDB code
     */
    private int minImdb;

    /**
     * Maximal IMDB code
     */
    private int maxImdb;

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public void initialize(final ImdbCode imdbCode) {
        minImdb = 1;
        maxImdb = Constants.MAX_IMDB_CODE;
    }

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public boolean isValid(final String imdbCode, final ConstraintValidatorContext constraintValidatorContext) {
        if (imdbCode == null) {
            return true;
        }
        if (imdbCode.isEmpty()) {
            return true;
        }
        if (!imdbCode.matches("\\d{1,7}")) {
            return false;
        }

        final int intValue = Integer.parseInt(imdbCode);
        return intValue >= minImdb && intValue <= maxImdb;
    }

}
