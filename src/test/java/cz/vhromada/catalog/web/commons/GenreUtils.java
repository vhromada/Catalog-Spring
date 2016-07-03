package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.web.fo.GenreFO;

/**
 * A class represents utility class for genres.
 *
 * @author Vladimir Hromada
 */
public final class GenreUtils {

    /**
     * Creates a new instance of GenreUtils.
     */
    private GenreUtils() {
    }

    /**
     * Returns FO for genre.
     *
     * @return FO for genre
     */
    public static GenreFO getGenreFO() {
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
    public static GenreTO getGenreTO() {
        final GenreTO genre = new GenreTO();
        genre.setId(1);
        genre.setName("Name");
        genre.setPosition(0);

        return genre;
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected FO for genre
     * @param actual   actual TO for genre
     */
    public static void assertGenreDeepEquals(final GenreFO expected, final GenreTO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
