package cz.vhromada.catalog.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.common.Language;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.utils.CollectionUtils;
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
        season.setId(CatalogUtils.ID);
        season.setNumber(CatalogUtils.NUMBER.toString());
        season.setStartYear(CatalogUtils.YEAR.toString());
        season.setEndYear(Integer.toString(CatalogUtils.YEAR + 1));
        season.setLanguage(Language.EN);
        season.setSubtitles(CollectionUtils.newList(Language.CZ));
        season.setNote(CatalogUtils.NOTE);
        season.setPosition(CatalogUtils.POSITION);

        return season;
    }

    /**
     * Returns season.
     *
     * @return season
     */
    public static Season getSeason() {
        final Season season = new Season();
        season.setId(CatalogUtils.ID);
        season.setNumber(CatalogUtils.NUMBER);
        season.setStartYear(CatalogUtils.YEAR);
        season.setEndYear(CatalogUtils.YEAR + 1);
        season.setLanguage(Language.EN);
        season.setSubtitles(CollectionUtils.newList(Language.CZ));
        season.setNote(CatalogUtils.NOTE);
        season.setPosition(CatalogUtils.POSITION);

        return season;
    }

    /**
     * Asserts season deep equals.
     *
     * @param expected expected FO for season
     * @param actual   actual season
     */
    public static void assertSeasonDeepEquals(final SeasonFO expected, final Season actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getNumber(), is(Integer.valueOf(expected.getNumber())));
        assertThat(actual.getStartYear(), is(Integer.valueOf(expected.getStartYear())));
        assertThat(actual.getEndYear(), is(Integer.valueOf(expected.getEndYear())));
        assertThat(actual.getLanguage(), is(expected.getLanguage()));
        assertThat(actual.getSubtitles(), is(expected.getSubtitles()));
        assertThat(actual.getNote(), is(expected.getNote()));
        assertThat(actual.getPosition(), is(expected.getPosition()));
    }

}
