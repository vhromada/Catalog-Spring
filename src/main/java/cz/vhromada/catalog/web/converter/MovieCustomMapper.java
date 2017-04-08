package cz.vhromada.catalog.web.converter;

import java.util.List;

import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.web.fo.MovieFO;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.util.CollectionUtils;

/**
 * A class represents custom mapper for movie.
 *
 * @author Vladimir Hromada
 */
public class MovieCustomMapper extends CustomMapper<MovieFO, Movie> {

    @Override
    public void mapAtoB(final MovieFO movieMO, final Movie movie, final MappingContext context) {
        super.mapAtoB(movieMO, movie, context);

        movie.setImdbCode(movieMO.getImdb() ? Integer.parseInt(movieMO.getImdbCode()) : -1);

        final List<Medium> media = movie.getMedia();
        if (!CollectionUtils.isEmpty(media)) {
            for (int i = 0; i < media.size(); i++) {
                media.get(i).setNumber(i + 1);
            }
        }
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void mapBtoA(final Movie movie, final MovieFO movieFO, final MappingContext context) {
        super.mapBtoA(movie, movieFO, context);

        if (movie.getImdbCode() < 1) {
            movieFO.setImdb(false);
            movieFO.setImdbCode(null);
        } else {
            movieFO.setImdb(true);
            movieFO.setImdbCode(Integer.toString(movie.getImdbCode()));
        }
    }

}
