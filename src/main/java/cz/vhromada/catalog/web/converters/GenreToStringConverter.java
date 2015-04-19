package cz.vhromada.catalog.web.converters;

import cz.vhromada.catalog.facade.to.GenreTO;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * A class represents converter between TO for genre and string.
 *
 * @author Vladimir Hromada
 */
public class GenreToStringConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (sourceFieldValue instanceof GenreTO && sourceClass == GenreTO.class && destinationClass == String.class) {
            return convertGenreTO((GenreTO) sourceFieldValue);
        } else if (sourceFieldValue instanceof String && sourceClass == String.class && destinationClass == GenreTO.class) {
            return convertString((String) sourceFieldValue);
        } else {
            throw new MappingException("Converter GenreToStringConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue + " and "
                    + sourceFieldValue);
        }
    }

    /**
     * Returns converted string to TO for song.
     *
     * @param source string
     * @return converted string to TO for song
     */
    private static GenreTO convertString(final String source) {
        final GenreTO song = new GenreTO();
        song.setId(Integer.parseInt(source));

        return song;
    }

    /**
     * Returns converted TO for song to string.
     *
     * @param source TO for song
     * @return converted TO for song to string
     */
    private static String convertGenreTO(final GenreTO source) {
        return Integer.toString(source.getId());
    }

}
