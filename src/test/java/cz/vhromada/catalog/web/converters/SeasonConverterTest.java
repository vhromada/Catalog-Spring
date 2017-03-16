package cz.vhromada.catalog.web.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.web.common.SeasonUtils;
import cz.vhromada.catalog.web.fo.SeasonFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link SeasonFO} to {@link Season}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class SeasonConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertSeasonFO() {
        final SeasonFO seasonFO = SeasonUtils.getSeasonFO();

        final Season season = converter.convert(seasonFO, Season.class);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for season.
     */
    @Test
    public void convertSeasonFO_NullSeasonFO() {
        assertThat(converter.convert(null, Season.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertSeason() {
        final Season season = SeasonUtils.getSeason();

        final SeasonFO seasonFO = converter.convert(season, SeasonFO.class);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null season.
     */
    @Test
    public void convertSeason_NullSeason() {
        assertThat(converter.convert(null, SeasonFO.class), is(nullValue()));
    }

}
