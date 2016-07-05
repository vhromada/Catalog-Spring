package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import cz.vhromada.catalog.commons.CollectionUtils;
import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.to.MediumTO;
import cz.vhromada.catalog.facade.to.MovieTO;
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
     * Returns TO for movie.
     *
     * @return TO for movie
     */
    public static MovieTO getMovieTO() {
        final MediumTO medium = new MediumTO();
        medium.setId(CatalogUtils.ID);
        medium.setNumber(CatalogUtils.NUMBER);
        medium.setLength(CatalogUtils.LENGTH);

        final MovieTO movie = new MovieTO();
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
        movie.setGenres(CollectionUtils.newList(GenreUtils.getGenreTO()));

        return movie;
    }

    /**
     * Asserts movie deep equals.
     *
     * @param expected expected FO for movie
     * @param actual   actual TO for movie
     */
    public static void assertMovieDeepEquals(final MovieFO expected, final MovieTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getYear());
        assertNotNull(actual.getImdbCode());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCzechName(), actual.getCzechName());
        assertEquals(expected.getOriginalName(), actual.getOriginalName());
        assertEquals(expected.getYear(), Integer.toString(actual.getYear()));
        assertEquals(expected.getLanguage(), actual.getLanguage());
        assertEquals(expected.getSubtitles(), actual.getSubtitles());
        assertEquals(expected.getCsfd(), actual.getCsfd());
        CatalogUtils.assertImdbCodeDeepEquals(expected.getImdb(), expected.getImdbCode(), actual.getImdbCode());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getPicture(), actual.getPicture());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertMediaDeepEquals(expected.getMedia(), actual.getMedia());
        GenreUtils.assertGenresDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Asserts media deep equals.
     *
     * @param expected expected list of FO for time
     * @param actual   actual list of TO for medium
     */
    public static void assertMediaDeepEquals(final List<TimeFO> expected, final List<MediumTO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertMediumDeepEquals(expected.get(i), actual.get(i), i);
        }
    }

    /**
     * Asserts medium deep equals.
     *
     * @param expected expected FO for time
     * @param actual   actual TO for medium
     * @param index    index
     */
    public static void assertMediumDeepEquals(final TimeFO expected, final MediumTO actual, final int index) {
        assertNotNull(actual);
        assertNull(actual.getId());
        assertEquals(index + 1, actual.getNumber());
        TimeUtils.assertTimeDeepEquals(expected, actual.getLength());
    }

    /**
     * Asserts movie deep equals.
     *
     * @param expected expected TO for movie
     * @param actual   actual FO for movie
     */
    public static void assertMovieDeepEquals(final MovieTO expected, final MovieFO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCzechName(), actual.getCzechName());
        assertEquals(expected.getOriginalName(), actual.getOriginalName());
        assertEquals(Integer.toString(expected.getYear()), actual.getYear());
        assertEquals(expected.getLanguage(), actual.getLanguage());
        assertEquals(expected.getSubtitles(), actual.getSubtitles());
        assertEquals(expected.getCsfd(), actual.getCsfd());
        CatalogUtils.assertImdbDeepEquals(expected.getImdbCode(), actual.getImdb(), actual.getImdbCode());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getPicture(), actual.getPicture());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
        assertMediumListDeepEquals(expected.getMedia(), actual.getMedia());
        GenreUtils.assertGenreListDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Asserts media deep equals.
     *
     * @param expected expected list of TO for medium
     * @param actual   actual list of FO for time
     */
    public static void assertMediumListDeepEquals(final List<MediumTO> expected, final List<TimeFO> actual) {
        assertNotNull(actual);
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            final TimeFO actualTime = actual.get(i);
            assertNotNull(actualTime);
            TimeUtils.assertTimeDeepEquals(actualTime, expected.get(i).getLength());
        }
    }

}
