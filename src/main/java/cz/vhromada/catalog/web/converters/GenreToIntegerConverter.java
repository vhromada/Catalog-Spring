package cz.vhromada.catalog.web.converters;

import cz.vhromada.catalog.facade.to.GenreTO;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * A class represents converter between TO for genre and integer.
 *
 * @author Vladimir Hromada
 */
public class GenreToIntegerConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (sourceFieldValue instanceof GenreTO && sourceClass == GenreTO.class && destinationClass == Integer.class) {
            return ((GenreTO) sourceFieldValue).getId();
        } else if (sourceFieldValue instanceof Integer && sourceClass == Integer.class && destinationClass == GenreTO.class) {
            final GenreTO genre = new GenreTO();
            genre.setId((Integer) sourceFieldValue);

            return genre;
        } else {
            throw new MappingException("Converter GenreToIntegerConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue + " and "
                    + sourceFieldValue);
        }
    }

}
