package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.fo.ShowFO;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * An abstract class represents mapper for shows.
 *
 * @author Vladimir Hromada
 */
@Mapper(uses = GenreIdMapper.class)
public abstract class ShowMapper {

    /**
     * Returns FO for show.
     *
     * @param source show
     * @return FO for show
     */
    @Mapping(target = "imdbCode", ignore = true)
    @Mapping(target = "imdb", ignore = true)
    public abstract ShowFO map(Show source);

    /**
     * Returns show.
     *
     * @param source FO for show
     * @return show
     */
    @Mapping(target = "imdbCode", ignore = true)
    public abstract Show mapBack(ShowFO source);

    /**
     * Maps additional data.
     *
     * @param source show
     * @param target FO for show
     */
    @AfterMapping
    void after(final Show source, @MappingTarget final ShowFO target) {
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
     * @param source FO for show
     * @param target show
     */
    @AfterMapping
    void after(final ShowFO source, @MappingTarget final Show target) {
        target.setImdbCode(source.getImdb() ? Integer.parseInt(source.getImdbCode()) : -1);
    }

}
