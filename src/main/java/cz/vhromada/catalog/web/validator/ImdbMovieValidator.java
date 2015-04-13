package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.validator.constraints.Imdb;

import org.apache.commons.lang3.StringUtils;

/**
 * A class represents show validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
public class ImdbMovieValidator implements ConstraintValidator<Imdb, MovieFO> {

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public void initialize(final Imdb imdb) {
    }

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public boolean isValid(final MovieFO show, final ConstraintValidatorContext constraintValidatorContext) {
        if (show == null) {
            return false;
        }
        if (!show.getImdb()) {
            return true;
        }

        return StringUtils.isNotEmpty(show.getImdbCode());
    }

}