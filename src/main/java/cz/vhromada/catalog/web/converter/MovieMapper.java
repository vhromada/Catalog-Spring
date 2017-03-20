package cz.vhromada.catalog.web.converter;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.converter.orika.MapperConfig;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

/**
 * A class represents mapper configuration for movie.
 *
 * @author Vladimir Hromada
 */
@Component("movieMapper")
public class MovieMapper implements MapperConfig {

    @Override
    public void config(final MapperFactory mapperFactory) {
        mapperFactory.classMap(MovieFO.class, Movie.class)
                .field("media{}", "media{length}")
                .exclude("imdbCode")
                .byDefault()
                .customize(new MovieCustomMapper())
                .register();
    }

}
