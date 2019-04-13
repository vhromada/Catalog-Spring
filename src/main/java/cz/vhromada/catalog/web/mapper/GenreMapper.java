package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.web.fo.GenreFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for genres.
 *
 * @author Vladimir Hromada
 */
@Mapper
public interface GenreMapper {

    /**
     * Returns FO for genre.
     *
     * @param source genre
     * @return FO for genre
     */
    GenreFO map(Genre source);

    /**
     * Returns genre.
     *
     * @param source FO for genre
     * @return genre
     */
    Genre mapBack(GenreFO source);

}
