package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.validator.constraints.Imdb;

import org.springframework.util.StringUtils;

/**
 * A class represents show validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
public class ImdbMovieValidator implements ConstraintValidator<Imdb, MovieFO> {

    @Override
    public void initialize(final Imdb imdb) {
    }

    @Override
    public boolean isValid(final MovieFO movie, final ConstraintValidatorContext constraintValidatorContext) {
        if (movie == null) {
            return false;
        }
        if (!movie.getImdb()) {
            return true;
        }

        return !StringUtils.isEmpty(movie.getImdbCode());
    }

}
