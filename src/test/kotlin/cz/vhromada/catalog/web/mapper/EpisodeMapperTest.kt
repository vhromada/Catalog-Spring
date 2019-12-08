package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Episode
import cz.vhromada.catalog.web.CatalogMapperTestConfiguration
import cz.vhromada.catalog.web.common.EpisodeUtils
import cz.vhromada.catalog.web.fo.EpisodeFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Episode] and [EpisodeFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogMapperTestConfiguration::class])
class EpisodeMapperTest {

    /**
     * Mapper for episodes
     */
    @Autowired
    private lateinit var mapper: EpisodeMapper

    /**
     * Test method for [EpisodeMapper.map].
     */
    @Test
    fun map() {
        val episode = EpisodeUtils.getEpisode()

        val episodeFO = mapper.map(episode)

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode)
    }

    /**
     * Test method for [EpisodeMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val episodeFO = EpisodeUtils.getEpisodeFO()

        val episode = mapper.mapBack(episodeFO)

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode)
    }

}
