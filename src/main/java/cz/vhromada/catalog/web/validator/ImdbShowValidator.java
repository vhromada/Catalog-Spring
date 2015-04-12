package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.catalog.web.validator.constraints.Imdb;

import org.apache.commons.lang3.StringUtils;

/**
 * A class represents show validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
public class ImdbShowValidator implements ConstraintValidator<Imdb, ShowFO> {

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public void initialize(final Imdb imdb) {
    }

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public boolean isValid(final ShowFO show, final ConstraintValidatorContext constraintValidatorContext) {
        if (show == null) {
            return false;
        }
        if (!show.getImdb()) {
            return true;
        }

        return StringUtils.isNotEmpty(show.getImdbCode());
    }

}
