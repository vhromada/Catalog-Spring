package cz.vhromada.catalog.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import cz.vhromada.catalog.common.Language;
import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.utils.CollectionUtils;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.fo.TimeFO;

/**
 * A class represents utility class for movies.
 *
 * @author Vladimir Hromada
 */
public final class MovieUtils {

    /**
     * Creates a new instance of MovieUtils.
     */
    private MovieUtils() {
    }

    /**
     * Returns FO for movie.
     *
     * @return FO for movie
     */
    public static MovieFO getMovieFO() {
        final MovieFO movie = new MovieFO();
        movie.setId(CatalogUtils.ID);
        movie.setCzechName("czName");
        movie.setOriginalName("origName");
        movie.setYear(Integer.toString(CatalogUtils.YEAR));
        movie.setLanguage(Language.EN);
        movie.setSubtitles(CollectionUtils.newList(Language.CZ));
        movie.setCsfd("Csfd");
        movie.setImdb(true);
        movie.setImdbCode("1000");
        movie.setWikiEn(CatalogUtils.EN_WIKI);
        movie.setWikiCz(CatalogUtils.CZ_WIKI);
        movie.setPicture("Picture");
        movie.setNote(CatalogUtils.NOTE);
        movie.setPosition(CatalogUtils.POSITION);
        movie.setMedia(CollectionUtils.newList(TimeUtils.getTimeFO()));
        movie.setGenres(CollectionUtils.newList(CatalogUtils.ID));

        return movie;
    }

    /**
     * Returns movie.
     *
     * @return movie
     */
    public static Movie getMovie() {
        final Medium medium = new Medium();
        medium.setId(CatalogUtils.ID);
        medium.setNumber(CatalogUtils.NUMBER);
        medium.setLength(CatalogUtils.LENGTH);

        final Movie movie = new Movie();
        movie.setId(CatalogUtils.ID);
        movie.setCzechName("czName");
        movie.setOriginalName("origName");
        movie.setYear(CatalogUtils.YEAR);
        movie.setLanguage(Language.EN);
        movie.setSubtitles(CollectionUtils.newList(Language.CZ));
        movie.setCsfd("Csfd");
        movie.setImdbCode(1000);
        movie.setWikiEn(CatalogUtils.EN_WIKI);
        movie.setWikiCz(CatalogUtils.CZ_WIKI);
        movie.setPicture("Picture");
        movie.setNote(CatalogUtils.NOTE);
        movie.setPosition(CatalogUtils.POSITION);
        movie.setMedia(CollectionUtils.newList(medium));
        movie.setGenres(CollectionUtils.newList(GenreUtils.getGenre()));

        return movie;
    }

    /**
     * Asserts movie deep equals.
     *
     * @param expected expected FO for movie
     * @param actual   actual movie
     */
    public static void assertMovieDeepEquals(final MovieFO expected, final Movie actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getCzechName(), is(expected.getCzechName()));
        assertThat(actual.getOriginalName(), is(expected.getOriginalName()));
        assertThat(actual.getYear(), is(Integer.parseInt(expected.getYear())));
        assertThat(actual.getLanguage(), is(expected.getLanguage()));
        assertThat(actual.getSubtitles(), is(expected.getSubtitles()));
        assertThat(actual.getCsfd(), is(expected.getCsfd()));
        CatalogUtils.assertImdbCodeDeepEquals(expected.getImdb(), expected.getImdbCode(), actual.getImdbCode());
        assertThat(actual.getWikiEn(), is(expected.getWikiEn()));
        assertThat(actual.getWikiCz(), is(expected.getWikiCz()));
        assertThat(actual.getPicture(), is(expected.getPicture()));
        assertThat(actual.getNote(), is(expected.getNote()));
        assertThat(actual.getPosition(), is(expected.getPosition()));
        assertMediaDeepEquals(expected.getMedia(), actual.getMedia());
        GenreUtils.assertGenresDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Asserts media deep equals.
     *
     * @param expected expected list of FO for time
     * @param actual   actual list of medium
     */
    public static void assertMediaDeepEquals(final List<TimeFO> expected, final List<Medium> actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            assertMediumDeepEquals(expected.get(i), actual.get(i), i);
        }
    }

    /**
     * Asserts medium deep equals.
     *
     * @param expected expected FO for time
     * @param actual   actual medium
     * @param index    index
     */
    public static void assertMediumDeepEquals(final TimeFO expected, final Medium actual, final int index) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(nullValue()));
        assertThat(actual.getNumber(), is(index + 1));
        TimeUtils.assertTimeDeepEquals(expected, actual.getLength());
    }

    /**
     * Asserts movie deep equals.
     *
     * @param expected expected movie
     * @param actual   actual FO for movie
     */
    public static void assertMovieDeepEquals(final Movie expected, final MovieFO actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getCzechName(), is(expected.getCzechName()));
        assertThat(actual.getOriginalName(), is(expected.getOriginalName()));
        assertThat(actual.getYear(), is(Integer.toString(expected.getYear())));
        assertThat(actual.getLanguage(), is(expected.getLanguage()));
        assertThat(actual.getSubtitles(), is(expected.getSubtitles()));
        assertThat(actual.getCsfd(), is(expected.getCsfd()));
        CatalogUtils.assertImdbDeepEquals(expected.getImdbCode(), actual.getImdb(), actual.getImdbCode());
        assertThat(actual.getWikiEn(), is(expected.getWikiEn()));
        assertThat(actual.getWikiCz(), is(expected.getWikiCz()));
        assertThat(actual.getPicture(), is(expected.getPicture()));
        assertThat(actual.getNote(), is(expected.getNote()));
        assertThat(actual.getPosition(), is(expected.getPosition()));
        assertMediumListDeepEquals(expected.getMedia(), actual.getMedia());
        GenreUtils.assertGenreListDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Asserts media deep equals.
     *
     * @param expected expected list of medium
     * @param actual   actual list of FO for time
     */
    public static void assertMediumListDeepEquals(final List<Medium> expected, final List<TimeFO> actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.size(), is(expected.size()));
        for (int i = 0; i < expected.size(); i++) {
            final TimeFO actualTime = actual.get(i);
            assertThat(actualTime, is(notNullValue()));
            TimeUtils.assertTimeDeepEquals(actualTime, expected.get(i).getLength());
        }
    }

}
