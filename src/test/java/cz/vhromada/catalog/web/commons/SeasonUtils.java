package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.commons.CollectionUtils;
import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.web.fo.SeasonFO;

/**
 * A class represents utility class for seasons.
 *
 * @author Vladimir Hromada
 */
public final class SeasonUtils {

    /**
     * Creates a new instance of SeasonUtils.
     */
    private SeasonUtils() {
    }

    /**
     * Returns FO for season.
     *
     * @return FO for season
     */
    public static SeasonFO getSeasonFO() {
        final SeasonFO season = new SeasonFO();
        season.setId(TestConstants.ID);
        season.setNumber(Integer.toString(TestConstants.NUMBER));
        season.setStartYear(Integer.toString(TestConstants.YEAR));
        season.setEndYear(Integer.toString(TestConstants.YEAR + 1));
        season.setLanguage(Language.EN);
        season.setSubtitles(CollectionUtils.newList(Language.CZ));
        season.setNote(TestConstants.NOTE);
        season.setPosition(TestConstants.POSITION);

        return season;
    }

    /**
     * Returns TO for season.
     *
     * @return TO for season
     */
    public static SeasonTO getSeasonTO() {
        final SeasonTO season = new SeasonTO();
        season.setId(TestConstants.ID);
        season.setNumber(TestConstants.NUMBER);
        season.setStartYear(TestConstants.YEAR);
        season.setEndYear(TestConstants.YEAR + 1);
        season.setLanguage(Language.EN);
        season.setSubtitles(CollectionUtils.newList(Language.CZ));
        season.setNote(TestConstants.NOTE);
        season.setPosition(TestConstants.POSITION);

        return season;
    }

    /**
     * Asserts season deep equals.
     *
     * @param expected expected FO for season
     * @param actual   actual TO for season
     */
    public static void assertSeasonDeepEquals(final SeasonFO expected, final SeasonTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getNumber());
        assertNotNull(actual.getStartYear());
        assertNotNull(actual.getEndYear());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getNumber(), Integer.toString(actual.getNumber()));
        assertEquals(expected.getStartYear(), Integer.toString(actual.getStartYear()));
        assertEquals(expected.getEndYear(), Integer.toString(actual.getEndYear()));
        assertEquals(expected.getLanguage(), actual.getLanguage());
        assertEquals(expected.getSubtitles(), actual.getSubtitles());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
