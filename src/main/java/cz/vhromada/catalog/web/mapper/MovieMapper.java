package cz.vhromada.catalog.web.mapper;

import java.util.List;

import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.fo.TimeFO;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.util.CollectionUtils;

/**
 * An interface represents mapper for movies.
 *
 * @author Vladimir Hromada
 */
@Mapper(uses = GenreIdMapper.class)
public abstract class MovieMapper {

    /**
     * Returns FO for movie.
     *
     * @param source movie
     * @return FO for movie
     */
    @Mapping(target = "imdbCode", ignore = true)
    @Mapping(target = "imdb", ignore = true)
    public abstract MovieFO map(Movie source);

    /**
     * Returns movie.
     *
     * @param source FO for movie
     * @return movie
     */
    @Mapping(target = "imdbCode", ignore = true)
    public abstract Movie mapBack(MovieFO source);

    /**
     * Returns FO for time.
     *
     * @param source medium
     * @return FO for time
     */
    TimeFO mapMedium(final Medium source) {
        if (source == null) {
            return null;
        }

        return Mappers.getMapper(TimeMapper.class).map(source.getLength());
    }

    /**
     * Returns medium.
     *
     * @param source FO for time
     * @return medium
     */

    Medium mapMediumBack(final TimeFO source) {
        if (source == null) {
            return null;
        }

        final Medium medium = new Medium();
        medium.setLength(Mappers.getMapper(TimeMapper.class).mapBack(source));
        return medium;
    }

    /**
     * Maps additional data.
     *
     * @param source movie
     * @param target FO for movie
     */
    @AfterMapping
    void after(final Movie source, @MappingTarget final MovieFO target) {
        if (source.getImdbCode() < 1) {
            target.setImdb(false);
            target.setImdbCode(null);
        } else {
            target.setImdb(true);
            target.setImdbCode(Integer.toString(source.getImdbCode()));
        }
    }

    /**
     * Maps additional data.
     *
     * @param source FO for movie
     * @param target movie
     */
    @AfterMapping
    void after(final MovieFO source, @MappingTarget final Movie target) {
        target.setImdbCode(source.getImdb() ? Integer.parseInt(source.getImdbCode()) : -1);

        final List<Medium> media = target.getMedia();
        if (!CollectionUtils.isEmpty(media)) {
            for (int i = 0; i < media.size(); i++) {
                media.get(i).setNumber(i + 1);
            }
        }
    }

}
