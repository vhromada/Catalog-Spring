package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.web.common.SeasonUtils;
import cz.vhromada.catalog.web.fo.SeasonFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link SeasonFO} to {@link Season}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class SeasonConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertSeasonFO() {
        final SeasonFO seasonFO = SeasonUtils.getSeasonFO();

        final Season season = converter.convert(seasonFO, Season.class);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for season.
     */
    @Test
    void convertSeasonFO_NullSeasonFO() {
        assertThat(converter.convert(null, Season.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertSeason() {
        final Season season = SeasonUtils.getSeason();

        final SeasonFO seasonFO = converter.convert(season, SeasonFO.class);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null season.
     */
    @Test
    void convertSeason_NullSeason() {
        assertThat(converter.convert(null, SeasonFO.class)).isNull();
    }

}
