package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
 * A class represents test for converter from {@link MovieTO} to {@link MovieFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class MovieTOToMovieFOConverterTest extends ObjectGeneratorTest {

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
        final MovieTO movieTO = objectGenerator.generate(MovieTO.class);
        movieTO.setImdbCode(objectGenerator.generate(Integer.class));

        final MovieFO movieFO = converter.convert(movieTO, MovieFO.class);

        assertMovieDeepEquals(movieTO, movieFO, true);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with not selected IMDB code.
     */
    @Test
    public void testConvertWithNotSelectedImdbCode() {
        final MovieTO movieTO = objectGenerator.generate(MovieTO.class);
        movieTO.setImdbCode(-1);

        final MovieFO movieFO = converter.convert(movieTO, MovieFO.class);

        assertMovieDeepEquals(movieTO, movieFO, false);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, MovieFO.class));
    }

    /**
     * Assert movie deep equals.
     *
     * @param expected expected movie
     * @param actual   actual movie
     * @param imdbCode true if IMDB code is selected
     */
    private static void assertMovieDeepEquals(final MovieTO expected, final MovieFO actual, final boolean imdbCode) {
        DeepAsserts.assertNotNull(actual, "imdbCode");
        DeepAsserts.assertEquals(expected, actual, "year", "media", "imdbCode", "imdb", "genres");
        DeepAsserts.assertEquals(Integer.toString(expected.getYear()), actual.getYear());
        assertMediaDeepEquals(expected.getMedia(), actual.getMedia());
        if (imdbCode) {
            assertTrue(actual.getImdb());
            DeepAsserts.assertNotNull(actual.getImdbCode());
            DeepAsserts.assertEquals(Integer.toString(expected.getImdbCode()), actual.getImdbCode());
        } else {
            assertFalse(actual.getImdb());
            assertNull(actual.getImdbCode());
        }
        assertGenresDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Assert media deep equals.
     *
     * @param expected expected media
     * @param actual   actual media
     */
    private static void assertMediaDeepEquals(final List<Integer> expected, final List<TimeFO> actual) {
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
    private static void assertMediumDeepEquals(final Integer expected, final TimeFO actual) {
        final Time time = new Time(expected);
        DeepAsserts.assertEquals(time, actual, "length", "hours", "minutes", "seconds");
        DeepAsserts.assertEquals(Integer.toString(time.getData(Time.TimeData.HOUR)), actual.getHours());
        DeepAsserts.assertEquals(Integer.toString(time.getData(Time.TimeData.MINUTE)), actual.getMinutes());
        DeepAsserts.assertEquals(Integer.toString(time.getData(Time.TimeData.SECOND)), actual.getSeconds());
    }

    /**
     * Assert genres deep equals.
     *
     * @param expected expected genres
     * @param actual   actual genres
     */
    private static void assertGenresDeepEquals(final List<GenreTO> expected, final List<String> actual) {
        DeepAsserts.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            DeepAsserts.assertEquals(Integer.toString(expected.get(i).getId()), actual.get(i));
        }
    }

}
