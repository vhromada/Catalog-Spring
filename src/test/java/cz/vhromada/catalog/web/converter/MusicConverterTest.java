package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.web.common.MusicUtils;
import cz.vhromada.catalog.web.fo.MusicFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link MusicFO} to {@link Music}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class MusicConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertMusicFO() {
        final MusicFO musicFO = MusicUtils.getMusicFO();

        final Music music = converter.convert(musicFO, Music.class);

        MusicUtils.assertMusicDeepEquals(musicFO, music);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for music.
     */
    @Test
    void convertMusicFO_NullMusicFO() {
        assertThat(converter.convert(null, Music.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertMusic() {
        final Music music = MusicUtils.getMusic();

        final MusicFO musicFO = converter.convert(music, MusicFO.class);

        MusicUtils.assertMusicDeepEquals(musicFO, music);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null music.
     */
    @Test
    void convertMusic_NullMusic() {
        assertThat(converter.convert(null, MusicFO.class)).isNull();
    }

}
