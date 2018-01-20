package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.web.common.MovieUtils;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link MovieFO} to {@link Movie}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class MovieConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with IMDB code.
     */
    @Test
    void convertMovieFO_Imdb() {
        final MovieFO movieFO = MovieUtils.getMovieFO();

        final Movie movie = converter.convert(movieFO, Movie.class);

        MovieUtils.assertMovieDeepEquals(movieFO, movie);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity without IMDB code.
     */
    @Test
    void convertMovieFO_NoImdb() {
        final MovieFO movieFO = MovieUtils.getMovieFO();
        movieFO.setImdb(false);
        movieFO.setImdbCode("");

        final Movie movie = converter.convert(movieFO, Movie.class);

        MovieUtils.assertMovieDeepEquals(movieFO, movie);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for movie.
     */
    @Test
    void convertMovieFO_NullMovieFO() {
        assertThat(converter.convert(null, Movie.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with IMDB code.
     */
    @Test
    void convertMovie_Imdb() {
        final Movie movie = MovieUtils.getMovie();

        final MovieFO movieFO = converter.convert(movie, MovieFO.class);

        MovieUtils.assertMovieDeepEquals(movie, movieFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO without IMDB code.
     */
    @Test
    void convertMovie_NoImdb() {
        final Movie movie = MovieUtils.getMovie();
        movie.setImdbCode(-1);

        final MovieFO movieFO = converter.convert(movie, MovieFO.class);

        MovieUtils.assertMovieDeepEquals(movie, movieFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with entity.
     */
    @Test
    void convertMovie_NullEntity() {
        assertThat(converter.convert(null, MovieFO.class)).isNull();
    }

}
