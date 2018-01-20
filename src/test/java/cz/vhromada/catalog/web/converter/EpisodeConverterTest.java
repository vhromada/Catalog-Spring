package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.web.common.EpisodeUtils;
import cz.vhromada.catalog.web.fo.EpisodeFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link EpisodeFO} to {@link Episode}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class EpisodeConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertEpisodeFO() {
        final EpisodeFO episodeFO = EpisodeUtils.getEpisodeFO();

        final Episode episode = converter.convert(episodeFO, Episode.class);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for episode.
     */
    @Test
    void convertEpisodeFO_NullEpisodeFO() {
        assertThat(converter.convert(null, Episode.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertEpisode() {
        final Episode episode = EpisodeUtils.getEpisode();

        final EpisodeFO episodeFO = converter.convert(episode, EpisodeFO.class);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null episode.
     */
    @Test
    void convertEpisode_NullEpisode() {
        assertThat(converter.convert(null, EpisodeFO.class)).isNull();
    }

}
