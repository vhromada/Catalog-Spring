package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.web.common.SeasonUtils;
import cz.vhromada.catalog.web.fo.SeasonFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Season} and {@link SeasonFO}.
 *
 * @author Vladimir Hromada
 */
class SeasonMapperTest {

    /**
     * Mapper for seasons
     */
    private SeasonMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SeasonMapper.class);
    }

    /**
     * Test method for {@link SeasonMapper#map(Season)}.
     */
    @Test
    void map() {
        final Season season = SeasonUtils.getSeason();

        final SeasonFO seasonFO = mapper.map(season);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season);
    }

    /**
     * Test method for {@link SeasonMapper#map(Season)} with null season.
     */
    @Test
    void map_NullSeason() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link SeasonMapper#mapBack(SeasonFO)}.
     */
    @Test
    void mapBack() {
        final SeasonFO seasonFO = SeasonUtils.getSeasonFO();

        final Season season = mapper.mapBack(seasonFO);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season);
    }

    /**
     * Test method for {@link SeasonMapper#mapBack(SeasonFO)} with null season.
     */
    @Test
    void mapBack_NullSeason() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
