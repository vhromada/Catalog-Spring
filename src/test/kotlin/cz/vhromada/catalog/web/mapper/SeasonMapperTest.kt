package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.web.CatalogMapperTestConfiguration
import cz.vhromada.catalog.web.common.SeasonUtils
import cz.vhromada.catalog.web.fo.SeasonFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Season] and [SeasonFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogMapperTestConfiguration::class])
class SeasonMapperTest {

    /**
     * Mapper for seasons
     */
    @Autowired
    private lateinit var mapper: SeasonMapper

    /**
     * Test method for [SeasonMapper.map].
     */
    @Test
    fun map() {
        val season = SeasonUtils.getSeason()

        val seasonFO = mapper.map(season)

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season)
    }

    /**
     * Test method for [SeasonMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val seasonFO = SeasonUtils.getSeasonFO()

        val season = mapper.mapBack(seasonFO)

        SeasonUtils.assertSeasonDeepEquals(seasonFO, season)
    }

}
