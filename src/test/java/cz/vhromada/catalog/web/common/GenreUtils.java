package cz.vhromada.catalog.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import cz.vhromada.catalog.entity.Genre;
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
     * Returns genre.
     *
     * @return genre
     */
    public static Genre getGenre() {
        final Genre genre = new Genre();
        genre.setId(CatalogUtils.ID);
        genre.setName(CatalogUtils.NAME);
        genre.setPosition(CatalogUtils.POSITION);

        return genre;
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected FO for genre
     * @param actual   actual genre
     */
    public static void assertGenreDeepEquals(final GenreFO expected, final Genre actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getPosition(), is(expected.getPosition()));
    }

    /**
     * Asserts genres deep equals.
     *
     * @param expected expected list of genres
     * @param actual   actual list of genre
     */
    public static void assertGenresDeepEquals(final List<Integer> expected, final List<Genre> actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertGenreDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected genre
     * @param actual   actual genre
     */
    public static void assertGenreDeepEquals(final Integer expected, final Genre actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected));
        assertThat(actual.getName(), is(nullValue()));
    }

    /**
     * Asserts genres deep equals.
     *
     * @param expected expected list of genre
     * @param actual   actual list of genres
     */
    public static void assertGenreListDeepEquals(final List<Genre> expected, final List<Integer> actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertGenreDeepEquals(expected.get(i), actual.get(i));
        }
    }

    /**
     * Asserts genre deep equals.
     *
     * @param expected expected genre
     * @param actual   actual genre
     */
    public static void assertGenreDeepEquals(final Genre expected, final Integer actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual, is(expected.getId()));
    }

}
