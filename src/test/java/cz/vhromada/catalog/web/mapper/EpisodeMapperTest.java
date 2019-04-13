package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.web.common.EpisodeUtils;
import cz.vhromada.catalog.web.fo.EpisodeFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Episode} and {@link EpisodeFO}.
 *
 * @author Vladimir Hromada
 */
class EpisodeMapperTest {

    /**
     * Mapper for episodes
     */
    private EpisodeMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(EpisodeMapper.class);
    }

    /**
     * Test method for {@link EpisodeMapper#map(Episode)}.
     */
    @Test
    void map() {
        final Episode episode = EpisodeUtils.getEpisode();

        final EpisodeFO episodeFO = mapper.map(episode);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode);
    }

    /**
     * Test method for {@link EpisodeMapper#map(Episode)} with null episode.
     */
    @Test
    void map_NullEpisode() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link EpisodeMapper#mapBack(EpisodeFO)}.
     */
    @Test
    void mapBack() {
        final EpisodeFO episodeFO = EpisodeUtils.getEpisodeFO();

        final Episode episode = mapper.mapBack(episodeFO);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode);
    }

    /**
     * Test method for {@link EpisodeMapper#mapBack(EpisodeFO)} with null episode.
     */
    @Test
    void mapBack_NullEpisode() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
