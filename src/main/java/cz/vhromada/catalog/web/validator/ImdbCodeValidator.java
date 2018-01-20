package cz.vhromada.catalog.web.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.utils.Constants;
import cz.vhromada.catalog.web.validator.constraints.ImdbCode;

/**
 * A class represents validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
public class ImdbCodeValidator implements ConstraintValidator<ImdbCode, String> {

    /**
     * IMDB code pattern
     */
    private static final Pattern PATTERN = Pattern.compile("\\d{1,7}");

    /**
     * Minimal IMDB code
     */
    private int minImdb;

    /**
     * Maximal IMDB code
     */
    private int maxImdb;

    @Override
    public void initialize(final ImdbCode imdbCode) {
        minImdb = 1;
        maxImdb = Constants.MAX_IMDB_CODE;
    }

    @Override
    public boolean isValid(final String imdbCode, final ConstraintValidatorContext constraintValidatorContext) {
        if (imdbCode == null) {
            return true;
        }
        if (imdbCode.isEmpty()) {
            return true;
        }
        if (!PATTERN.matcher(imdbCode).matches()) {
            return false;
        }

        final int intValue = Integer.parseInt(imdbCode);
        return intValue >= minImdb && intValue <= maxImdb;
    }

}
