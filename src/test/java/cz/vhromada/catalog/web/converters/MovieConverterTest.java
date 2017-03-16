package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.web.common.MovieUtils;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link MovieFO} to {@link Movie}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class MovieConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with IMDB code.
     */
    @Test
    public void convertMovieFO_Imdb() {
        final MovieFO movieFO = MovieUtils.getMovieFO();

        final Movie movie = converter.convert(movieFO, Movie.class);

        MovieUtils.assertMovieDeepEquals(movieFO, movie);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity without IMDB code.
     */
    @Test
    public void convertMovieFO_NoImdb() {
        final MovieFO movieFO = MovieUtils.getMovieFO();
        movieFO.setImdb(false);

        final Movie movie = converter.convert(movieFO, Movie.class);

        MovieUtils.assertMovieDeepEquals(movieFO, movie);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for movie.
     */
    @Test
    public void convertMovieFO_NullMovieFO() {
        assertNull(converter.convert(null, Movie.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with IMDB code.
     */
    @Test
    public void convertMovie_Imdb() {
        final Movie movie = MovieUtils.getMovie();

        final MovieFO movieFO = converter.convert(movie, MovieFO.class);

        MovieUtils.assertMovieDeepEquals(movie, movieFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO without IMDB code.
     */
    @Test
    public void convertMovie_NoImdb() {
        final Movie movie = MovieUtils.getMovie();
        movie.setImdbCode(-1);

        final MovieFO movieFO = converter.convert(movie, MovieFO.class);

        MovieUtils.assertMovieDeepEquals(movie, movieFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with entity.
     */
    @Test
    public void convertMovie_NullEntity() {
        assertNull(converter.convert(null, MovieFO.class));
    }

}
