package cz.vhromada.catalog.web.converters;

import java.util.ArrayList;
import java.util.List;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.web.fo.BookFO;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * A class represents converter between FO for book and TO for book.
 *
 * @author Vladimir Hromada
 */
public class BookConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (sourceFieldValue instanceof BookFO && sourceClass == BookFO.class && destinationClass == BookTO.class) {
            return convertBookFO((BookFO) sourceFieldValue);
        } else if (sourceFieldValue instanceof BookTO && sourceClass == BookTO.class && destinationClass == BookFO.class) {
            return convertBookTO((BookTO) sourceFieldValue);
        } else {
            throw new MappingException("Converter BookConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue
                    + " and " + sourceFieldValue);
        }
    }

    /**
     * Returns converted FO for book to TO for book.
     *
     * @param source FO for book
     * @return converted FO for book to TO for book
     */
    private static BookTO convertBookFO(final BookFO source) {
        final BookTO book = new BookTO();
        book.setId(source.getId());
        book.setAuthor(source.getAuthor());
        book.setTitle(source.getTitle());
        final List<Language> languages = new ArrayList<>();
        if (source.isCzech()) {
            languages.add(Language.CZ);
        }
        if (source.isEnglish()) {
            languages.add(Language.EN);
        }
        book.setLanguages(languages);
        book.setNote(source.getNote());
        book.setPosition(source.getPosition());

        return book;
    }

    /**
     * Returns converted TO for book to FO for book.
     *
     * @param source TO for book
     * @return converted TO for book to FO for book
     */
    private static BookFO convertBookTO(final BookTO source) {
        final BookFO book = new BookFO();
        book.setId(source.getId());
        book.setAuthor(source.getAuthor());
        book.setTitle(source.getTitle());
        for (final Language language : source.getLanguages()) {
            switch (language) {
                case CZ:
                    book.setCzech(true);
                    break;
                case EN:
                    book.setEnglish(true);
                    break;
                default:
                    throw new IllegalArgumentException("Bad language");
            }
        }
        book.setNote(source.getNote());
        book.setPosition(source.getPosition());

        return book;
    }

}
