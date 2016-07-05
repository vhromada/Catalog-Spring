package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.MovieTO;
import cz.vhromada.catalog.web.commons.MovieUtils;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link MovieFO} to {@link MovieTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class MovieConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with IMDB code.
     */
    @Test
    public void testConvertMovieFO_Imdb() {
        final MovieFO movieFO = MovieUtils.getMovieFO();

        final MovieTO movieTO = converter.convert(movieFO, MovieTO.class);

        MovieUtils.assertMovieDeepEquals(movieFO, movieTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO without IMDB code.
     */
    @Test
    public void testConvertMovieFO_NoImdb() {
        final MovieFO movieFO = MovieUtils.getMovieFO();
        movieFO.setImdb(false);

        final MovieTO movieTO = converter.convert(movieFO, MovieTO.class);

        MovieUtils.assertMovieDeepEquals(movieFO, movieTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertMovieFO_NullArgument() {
        assertNull(converter.convert(null, MovieTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with IMDB code.
     */
    @Test
    public void testConvertMovieTO_Imdb() {
        final MovieTO movieTO = MovieUtils.getMovieTO();

        final MovieFO movieFO = converter.convert(movieTO, MovieFO.class);

        MovieUtils.assertMovieDeepEquals(movieTO, movieFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO without IMDB code.
     */
    @Test
    public void testConvertMovieTO_NoImdb() {
        final MovieTO movieTO = MovieUtils.getMovieTO();
        movieTO.setImdbCode(-1);

        final MovieFO movieFO = converter.convert(movieTO, MovieFO.class);

        MovieUtils.assertMovieDeepEquals(movieTO, movieFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertMovieTO_NullArgument() {
        assertNull(converter.convert(null, MovieFO.class));
    }

}