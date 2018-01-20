package cz.vhromada.catalog.web.common;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getName()).isEqualTo(expected.getName());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

    /**
     * Asserts genres deep equals.
     *
     * @param expected expected list of genres
     * @param actual   actual list of genre
     */
    public static void assertGenresDeepEquals(final List<Integer> expected, final List<Genre> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(actual.size()).isEqualTo(expected.size());
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
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected);
            softly.assertThat(actual.getName()).isNull();
        });
    }

    /**
     * Asserts genres deep equals.
     *
     * @param expected expected list of genre
     * @param actual   actual list of genres
     */
    public static void assertGenreListDeepEquals(final List<Genre> expected, final List<Integer> actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertThat(actual.size()).isEqualTo(expected.size());
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
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> softly.assertThat(actual).isEqualTo(expected.getId()));
    }

}
