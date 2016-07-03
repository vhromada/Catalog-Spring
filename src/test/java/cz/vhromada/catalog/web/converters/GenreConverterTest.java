package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.web.fo.GenreFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link GenreFO} to {@link GenreTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class GenreConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO.
     */
    @Test
    public void testConvertGenreFO() {
        final GenreFO genreFO = getGenreFO();

        final GenreTO genreTO = converter.convert(genreFO, GenreTO.class);

        assertGenreDeepEquals(genreTO, genreFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertGenreFO_NullArgument() {
        assertNull(converter.convert(null, GenreTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertGenreTO() {
        final GenreTO genreTO = getGenreTO();

        final GenreFO genreFO = converter.convert(genreTO, GenreFO.class);

        assertGenreDeepEquals(genreTO, genreFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertGenreTO_NullArgument() {
        assertNull(converter.convert(null, GenreFO.class));
    }

    /**
     * Returns FO for genre.
     *
     * @return FO for genre
     */
    private static GenreFO getGenreFO() {
        final GenreFO genre = new GenreFO();
        genre.setId(1);
        genre.setName("Name");
        genre.setPosition(0);

        return genre;
    }

    /**
     * Returns TO for genre.
     *
     * @return TO for genre
     */
    private static GenreTO getGenreTO() {
        final GenreTO genre = new GenreTO();
        genre.setId(1);
        genre.setName("Name");
        genre.setPosition(0);

        return genre;
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected TO for genre
     * @param actual   actual FO for genre
     */
    public static void assertGenreDeepEquals(final GenreTO expected, final GenreFO actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
