package cz.vhromada.catalog.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.web.common.MusicUtils;
import cz.vhromada.catalog.web.fo.MusicFO;
import cz.vhromada.converter.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link MusicFO} to {@link Music}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class MusicConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertMusicFO() {
        final MusicFO musicFO = MusicUtils.getMusicFO();

        final Music music = converter.convert(musicFO, Music.class);

        MusicUtils.assertMusicDeepEquals(musicFO, music);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for music.
     */
    @Test
    public void convertMusicFO_NullMusicFO() {
        assertThat(converter.convert(null, Music.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertMusic() {
        final Music music = MusicUtils.getMusic();

        final MusicFO musicFO = converter.convert(music, MusicFO.class);

        MusicUtils.assertMusicDeepEquals(musicFO, music);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null music.
     */
    @Test
    public void convertMusic_NullMusic() {
        assertThat(converter.convert(null, MusicFO.class), is(nullValue()));
    }

}
