package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.web.common.MusicUtils;
import cz.vhromada.catalog.web.fo.MusicFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Music} and {@link MusicFO}.
 *
 * @author Vladimir Hromada
 */
class MusicMapperTest {

    /**
     * Mapper for music
     */
    private MusicMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MusicMapper.class);
    }

    /**
     * Test method for {@link MusicMapper#map(Music)}.
     */
    @Test
    void map() {
        final Music music = MusicUtils.getMusic();

        final MusicFO musicFO = mapper.map(music);

        MusicUtils.assertMusicDeepEquals(musicFO, music);
    }

    /**
     * Test method for {@link MusicMapper#map(Music)} with null music.
     */
    @Test
    void map_NullMusic() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link MusicMapper#mapBack(MusicFO)}.
     */
    @Test
    void mapBack() {
        final MusicFO musicFO = MusicUtils.getMusicFO();

        final Music music = mapper.mapBack(musicFO);

        MusicUtils.assertMusicDeepEquals(musicFO, music);
    }

    /**
     * Test method for {@link MusicMapper#mapBack(MusicFO)} with null music.
     */
    @Test
    void mapBack_NullMusic() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
