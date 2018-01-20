package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.web.common.SongUtils;
import cz.vhromada.catalog.web.fo.SongFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link SongFO} to {@link Song}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class SongConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertSongFO() {
        final SongFO songFO = SongUtils.getSongFO();

        final Song song = converter.convert(songFO, Song.class);

        SongUtils.assertSongDeepEquals(songFO, song);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for song.
     */
    @Test
    void convertSongFO_NullSongFO() {
        assertThat(converter.convert(null, Song.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertSong() {
        final Song song = SongUtils.getSong();

        final SongFO songFO = converter.convert(song, SongFO.class);

        SongUtils.assertSongDeepEquals(songFO, song);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null song.
     */
    @Test
    void convertSong_NullSong() {
        assertThat(converter.convert(null, SongFO.class)).isNull();
    }

}
