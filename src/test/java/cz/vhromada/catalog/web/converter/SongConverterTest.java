package cz.vhromada.catalog.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.web.common.SongUtils;
import cz.vhromada.catalog.web.fo.SongFO;
import cz.vhromada.converter.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link SongFO} to {@link Song}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class SongConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertSongFO() {
        final SongFO songFO = SongUtils.getSongFO();

        final Song song = converter.convert(songFO, Song.class);

        SongUtils.assertSongDeepEquals(songFO, song);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for song.
     */
    @Test
    public void convertSongFO_NullSongFO() {
        assertThat(converter.convert(null, Song.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertSong() {
        final Song song = SongUtils.getSong();

        final SongFO songFO = converter.convert(song, SongFO.class);

        SongUtils.assertSongDeepEquals(songFO, song);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null song.
     */
    @Test
    public void convertSong_NullSong() {
        assertThat(converter.convert(null, SongFO.class), is(nullValue()));
    }

}
