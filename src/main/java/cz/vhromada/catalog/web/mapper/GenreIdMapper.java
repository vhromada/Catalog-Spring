package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Genre;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for genre IDs.
 *
 * @author Vladimir Hromada
 */
@Mapper
public abstract class GenreIdMapper {

    /**
     * Returns ID.
     *
     * @param source genre
     * @return ID
     */
    public Integer map(final Genre source) {
        if (source == null) {
            return null;
        }

        return source.getId();
    }

    /**
     * Returns genre.
     *
     * @param source ID
     * @return genre
     */
    public Genre mapBack(final Integer source) {
        if (source == null) {
            return null;
        }

        final Genre genre = new Genre();
        genre.setId(source);
        return genre;
    }

}
