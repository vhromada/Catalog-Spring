package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.commons.CollectionUtils;
import cz.vhromada.catalog.facade.to.MediumTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.fo.ShowFO;

/**
 * A class represents utility class for shows.
 *
 * @author Vladimir Hromada
 */
public final class ShowUtils {

    /**
     * Creates a new instance of ShowUtils.
     */
    private ShowUtils() {
    }

    /**
     * Returns FO for show.
     *
     * @return FO for show
     */
    public static ShowFO getShowFO() {
        final MediumTO medium = new MediumTO();
        medium.setId(TestConstants.ID);
        medium.setNumber(TestConstants.NUMBER);
        medium.setLength(TestConstants.LENGTH);

        final ShowFO show = new ShowFO();
        show.setId(TestConstants.ID);
        show.setCzechName("czName");
        show.setOriginalName("origName");
        show.setCsfd("Csfd");
        show.setImdb(true);
        show.setImdbCode("1000");
        show.setWikiEn(TestConstants.EN_WIKI);
        show.setWikiCz(TestConstants.CZ_WIKI);
        show.setPicture("Picture");
        show.setNote(TestConstants.NOTE);
        show.setGenres(CollectionUtils.newList(TestConstants.NAME));
        show.setPosition(TestConstants.POSITION);

        return show;
    }

    /**
     * Returns TO for show.
     *
     * @return TO for show
     */
    public static ShowTO getShowTO() {
        final MediumTO medium = new MediumTO();
        medium.setId(TestConstants.ID);
        medium.setNumber(TestConstants.NUMBER);
        medium.setLength(TestConstants.LENGTH);

        final ShowTO show = new ShowTO();
        show.setId(TestConstants.ID);
        show.setCzechName("czName");
        show.setOriginalName("origName");
        show.setCsfd("Csfd");
        show.setImdbCode(1000);
        show.setWikiEn(TestConstants.EN_WIKI);
        show.setWikiCz(TestConstants.CZ_WIKI);
        show.setPicture("Picture");
        show.setNote(TestConstants.NOTE);
        show.setGenres(CollectionUtils.newList(GenreUtils.getGenreTO()));
        show.setPosition(TestConstants.POSITION);

        return show;
    }

    /**
     * Asserts show deep equals.
     *
     * @param expected expected FO for show
     * @param actual   actual TO for show
     */
    public static void assertShowDeepEquals(final ShowFO expected, final ShowTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getImdbCode());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCzechName(), actual.getCzechName());
        assertEquals(expected.getOriginalName(), actual.getOriginalName());
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
