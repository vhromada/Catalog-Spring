package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.commons.CollectionUtils;
import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.to.MediumTO;
import cz.vhromada.catalog.facade.to.MovieTO;
import cz.vhromada.catalog.web.fo.MovieFO;

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
        final MediumTO medium = new MediumTO();
        medium.setId(TestConstants.ID);
        medium.setNumber(TestConstants.NUMBER);
        medium.setLength(TestConstants.LENGTH);

        final MovieFO movie = new MovieFO();
        movie.setId(TestConstants.ID);
        movie.setCzechName("czName");
        movie.setOriginalName("origName");
        movie.setYear(Integer.toString(TestConstants.YEAR));
        movie.setLanguage(Language.EN);
        movie.setSubtitles(CollectionUtils.newList(Language.CZ));
        movie.setCsfd("Csfd");
        movie.setImdb(true);
        movie.setImdbCode("1000");
        movie.setWikiEn(TestConstants.EN_WIKI);
        movie.setWikiCz(TestConstants.CZ_WIKI);
        movie.setPicture("Picture");
        movie.setNote(TestConstants.NOTE);
        movie.setMedia(CollectionUtils.newList(TimeUtils.getTimeFO()));
        movie.setGenres(CollectionUtils.newList(TestConstants.NAME));
        movie.setPosition(TestConstants.POSITION);

        return movie;
    }

    /**
     * Returns TO for movie.
     *
     * @return TO for movie
     */
    public static MovieTO getMovieTO() {
        final MediumTO medium = new MediumTO();
        medium.setId(TestConstants.ID);
        medium.setNumber(TestConstants.NUMBER);
        medium.setLength(TestConstants.LENGTH);

        final MovieTO movie = new MovieTO();
        movie.setId(TestConstants.ID);
        movie.setCzechName("czName");
        movie.setOriginalName("origName");
        movie.setYear(TestConstants.YEAR);
        movie.setLanguage(Language.EN);
        movie.setSubtitles(CollectionUtils.newList(Language.CZ));
        movie.setCsfd("Csfd");
        movie.setImdbCode(1000);
        movie.setWikiEn(TestConstants.EN_WIKI);
        movie.setWikiCz(TestConstants.CZ_WIKI);
        movie.setPicture("Picture");
        movie.setNote(TestConstants.NOTE);
        movie.setMedia(CollectionUtils.newList(medium));
        movie.setGenres(CollectionUtils.newList(GenreUtils.getGenreTO()));
        movie.setPosition(TestConstants.POSITION);

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
        //assertEquals(expected.getImdbCode(), Integer.toString(actual.getImdbCode()));
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getPicture(), actual.getPicture());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
        //TODO vhromada 03.07.2016: test
//        MediumUtils.assertMediumListDeepEquals(expected.getMedia(), actual.getMedia());
//        GenreUtils.assertGenreListDeepEquals(expected.getGenres(), actual.getGenres());
    }

}
