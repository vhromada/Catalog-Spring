package cz.vhromada.catalog.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import cz.vhromada.catalog.web.fo.BookFO;
import cz.vhromada.catalog.web.validator.constraints.Languages;

/**
 * A class represents movie validator for languages constraint.
 *
 * @author Vladimir Hromada
 */
public class LanguagesValidator implements ConstraintValidator<Languages, BookFO> {

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public void initialize(final Languages languages) {

    }

    @Override
    @SuppressWarnings("ParameterNameDiffersFromOverriddenParameter")
    public boolean isValid(final BookFO book, final ConstraintValidatorContext constraintValidatorContext) {
        if (book == null) {
            return false;
        }

        return book.isCzech() || book.isEnglish();
    }

}
