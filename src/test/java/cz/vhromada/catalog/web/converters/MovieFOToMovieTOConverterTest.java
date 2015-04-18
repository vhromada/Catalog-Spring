package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.MovieTO;
import cz.vhromada.catalog.web.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.converters.Converter;
import cz.vhromada.generator.ObjectGenerator;
import cz.vhromada.test.DeepAsserts;

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
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class MovieFOToMovieTOConverterTest extends ObjectGeneratorTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Instance of {@link ObjectGenerator}
     */
    @Autowired
    private ObjectGenerator objectGenerator;

    /**
     * Test method for {@link Converter#convert(Object, Class)} with selected IMDB code.
     */
    @Test
    public void testConvertWithSelectedImdbCode() {
        final MovieFO movieFO = newMovieFO(true);

        final MovieTO movieTO = converter.convert(movieFO, MovieTO.class);

        assertMovieDeepEquals(movieFO, movieTO, true);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with not selected IMDB code.
     */
    @Test
    public void testConvertWithNotSelectedImdbCode() {
        final MovieFO movieFO = newMovieFO(false);

        final MovieTO movieTO = converter.convert(movieFO, MovieTO.class);

        assertMovieDeepEquals(movieFO, movieTO, false);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, MovieTO.class));
    }

    /**
     * Returns {@link MovieFO}.
     *
     * @param imdbCode true if IMDB code is selected
     * @return {@link MovieFO}
     */
    private MovieFO newMovieFO(final boolean imdbCode) {
        final MovieFO movie = objectGenerator.generate(MovieFO.class);
        movie.setYear(Integer.toString(objectGenerator.generate(Integer.class)));
        movie.setMedia(Arrays.asList(newTimeFO(), newTimeFO()));
        movie.setGenres(Arrays.asList(Integer.toString(objectGenerator.generate(Integer.class)), Integer.toString(objectGenerator.generate(Integer.class))));
        movie.setImdb(imdbCode);
        if (imdbCode) {
            movie.setImdbCode(Integer.toString(objectGenerator.generate(Integer.class)));
        }

        return movie;
    }

    /**
     * Returns {@link TimeFO}.
     *
     * @return {@link MovieFO}
     */
    private TimeFO newTimeFO() {
        final Time length = objectGenerator.generate(Time.class);
        final TimeFO time = new TimeFO();
        time.setHours(Integer.toString(length.getData(Time.TimeData.HOUR)));
        time.setMinutes(Integer.toString(length.getData(Time.TimeData.MINUTE)));
        time.setSeconds(Integer.toString(length.getData(Time.TimeData.SECOND)));

        return time;
    }

    /**
     * Assert movie deep equals.
     *
     * @param expected expected movie
     * @param actual   actual movie
     * @param imdbCode true if IMDB code is selected
     */
    private static void assertMovieDeepEquals(final MovieFO expected, final MovieTO actual, final boolean imdbCode) {
        DeepAsserts.assertNotNull(actual);
        DeepAsserts.assertEquals(expected, actual, "year", "media", "imdbCode", "imdb", "genres");
        DeepAsserts.assertEquals(Integer.valueOf(expected.getYear()), actual.getYear());
        assertMediaDeepEquals(expected.getMedia(), actual.getMedia());
        if (imdbCode) {
            DeepAsserts.assertEquals(Integer.valueOf(expected.getImdbCode()), actual.getImdbCode());
        } else {
            DeepAsserts.assertEquals(-1, actual.getImdbCode());
        }
        assertGenresDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Assert media deep equals.
     *
     * @param expected expected media
     * @param actual   actual media
     */
    private static void assertMediaDeepEquals(final List<TimeFO> expected, final List<Integer> actual) {
        DeepAsserts.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertMediumDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Assert medium deep equals.
     *
     * @param expected expected medium
     * @param actual   actual medium
     */
    private static void assertMediumDeepEquals(final TimeFO expected, final Integer actual) {
        final int hours = Integer.valueOf(expected.getHours());
        final int minutes = Integer.valueOf(expected.getMinutes());
        final int seconds = Integer.valueOf(expected.getSeconds());
        final Time length = new Time(hours, minutes, seconds);
        DeepAsserts.assertEquals(length.getLength(), actual);
    }

    /**
     * Assert genres deep equals.
     *
     * @param expected expected genres
     * @param actual   actual genres
     */
    private static void assertGenresDeepEquals(final List<String> expected, final List<GenreTO> actual) {
        DeepAsserts.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            DeepAsserts.assertEquals(Integer.valueOf(expected.get(i)), actual.get(i).getId());
        }
    }

}
