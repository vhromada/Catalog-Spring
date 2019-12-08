package cz.vhromada.catalog.web.validator

import cz.vhromada.catalog.web.fo.ShowFO
import cz.vhromada.catalog.web.validator.constraints.Imdb
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

/**
 * A class represents show validator for IMDB code constraint.
 *
 * @author Vladimir Hromada
 */
class ImdbShowValidator : ConstraintValidator<Imdb, ShowFO> {

    override fun isValid(show: ShowFO?, constraintValidatorContext: ConstraintValidatorContext): Boolean {
        if (show == null) {
            return false
        }
        return if (!show.imdb) {
            true
        } else !show.imdbCode.isNullOrBlank()
    }

}
