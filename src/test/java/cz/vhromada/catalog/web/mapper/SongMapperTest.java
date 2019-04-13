package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.web.common.SongUtils;
import cz.vhromada.catalog.web.fo.SongFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Song} and {@link SongFO}.
 *
 * @author Vladimir Hromada
 */
class SongMapperTest {

    /**
     * Mapper for songs
     */
    private SongMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(SongMapper.class);
    }

    /**
     * Test method for {@link SongMapper#map(Song)}.
     */
    @Test
    void map() {
        final Song song = SongUtils.getSong();

        final SongFO songFO = mapper.map(song);

        SongUtils.assertSongDeepEquals(songFO, song);
    }

    /**
     * Test method for {@link SongMapper#map(Song)} with null song.
     */
    @Test
    void map_NullSong() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link SongMapper#mapBack(SongFO)}.
     */
    @Test
    void mapBack() {
        final SongFO songFO = SongUtils.getSongFO();

        final Song song = mapper.mapBack(songFO);

        SongUtils.assertSongDeepEquals(songFO, song);
    }

    /**
     * Test method for {@link SongMapper#mapBack(SongFO)} with null song.
     */
    @Test
    void mapBack_NullSong() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
