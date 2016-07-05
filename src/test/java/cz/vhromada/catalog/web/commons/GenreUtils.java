package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

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
        genre.setId(CatalogUtils.ID);
        genre.setName(CatalogUtils.NAME);
        genre.setPosition(CatalogUtils.POSITION);

        return genre;
    }

    /**
     * Returns TO for genre.
     *
     * @return TO for genre
     */
    public static GenreTO getGenreTO() {
        final GenreTO genre = new GenreTO();
        genre.setId(CatalogUtils.ID);
        genre.setName(CatalogUtils.NAME);
        genre.setPosition(CatalogUtils.POSITION);

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

    /**
     * Asserts genres deep equals.
     *
     * @param expected expected list of genres
     * @param actual   actual list of TO for genre
     */
    public static void assertGenresDeepEquals(final List<Integer> expected, final List<GenreTO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertGenreDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected genre
     * @param actual   actual TO for genre
     */
    public static void assertGenreDeepEquals(final Integer expected, final GenreTO actual) {
        assertNotNull(actual);
        assertEquals(expected, actual.getId());
        assertNull(actual.getName());
    }

    /**
     * Asserts genres deep equals.
     *
     * @param expected expected list of TO for genre
     * @param actual   actual list of genres
     */
    public static void assertGenreListDeepEquals(final List<GenreTO> expected, final List<Integer> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertGenreDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected genre
     * @param actual   actual TO for genre
     */
    public static void assertGenreDeepEquals(final GenreTO expected, final Integer actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual);
    }

}
