package cz.vhromada.catalog.web.converter;

import cz.vhromada.catalog.entity.Genre;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

/**
 * A class represents converter between {@link Genre} and {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@Component("genreConverter")
public class GenreConverter extends BidirectionalConverter<Genre, Integer> {

    @Override
    public Integer convertTo(final Genre source, final Type<Integer> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        return source.getId();
    }

    @Override
    public Genre convertFrom(final Integer source, final Type<Genre> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        final Genre genre = new Genre();
        genre.setId(source);

        return genre;
    }

}
