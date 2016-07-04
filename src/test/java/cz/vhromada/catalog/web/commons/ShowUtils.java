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
        medium.setId(CatalogUtils.ID);
        medium.setNumber(CatalogUtils.NUMBER);
        medium.setLength(CatalogUtils.LENGTH);

        final ShowFO show = new ShowFO();
        show.setId(CatalogUtils.ID);
        show.setCzechName("czName");
        show.setOriginalName("origName");
        show.setCsfd("Csfd");
        show.setImdb(true);
        show.setImdbCode("1000");
        show.setWikiEn(CatalogUtils.EN_WIKI);
        show.setWikiCz(CatalogUtils.CZ_WIKI);
        show.setPicture("Picture");
        show.setNote(CatalogUtils.NOTE);
        show.setPosition(CatalogUtils.POSITION);
        show.setGenres(CollectionUtils.newList(CatalogUtils.ID));

        return show;
    }

    /**
     * Returns TO for show.
     *
     * @return TO for show
     */
    public static ShowTO getShowTO() {
        final MediumTO medium = new MediumTO();
        medium.setId(CatalogUtils.ID);
        medium.setNumber(CatalogUtils.NUMBER);
        medium.setLength(CatalogUtils.LENGTH);

        final ShowTO show = new ShowTO();
        show.setId(CatalogUtils.ID);
        show.setCzechName("czName");
        show.setOriginalName("origName");
        show.setCsfd("Csfd");
        show.setImdbCode(1000);
        show.setWikiEn(CatalogUtils.EN_WIKI);
        show.setWikiCz(CatalogUtils.CZ_WIKI);
        show.setPicture("Picture");
        show.setNote(CatalogUtils.NOTE);
        show.setPosition(CatalogUtils.POSITION);
        show.setGenres(CollectionUtils.newList(GenreUtils.getGenreTO()));

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
        CatalogUtils.assertImdbCodeDeepEquals(expected.getImdb(), expected.getImdbCode(), actual.getImdbCode());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getPicture(), actual.getPicture());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
        GenreUtils.assertGenresDeepEquals(expected.getGenres(), actual.getGenres());
    }

    /**
     * Asserts show deep equals.
     *
     * @param expected expected TO for show
     * @param actual   actual FO for show
     */
    public static void assertShowDeepEquals(final ShowTO expected, final ShowFO actual) {
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCzechName(), actual.getCzechName());
        assertEquals(expected.getOriginalName(), actual.getOriginalName());
        assertEquals(expected.getCsfd(), actual.getCsfd());
        CatalogUtils.assertImdbDeepEquals(expected.getImdbCode(), actual.getImdb(), actual.getImdbCode());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getPicture(), actual.getPicture());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
        GenreUtils.assertGenreListDeepEquals(expected.getGenres(), actual.getGenres());
    }
}
