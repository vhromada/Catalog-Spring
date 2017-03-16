package cz.vhromada.catalog.web.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.web.common.EpisodeUtils;
import cz.vhromada.catalog.web.fo.EpisodeFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link EpisodeFO} to {@link Episode}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class EpisodeConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertEpisodeFO() {
        final EpisodeFO episodeFO = EpisodeUtils.getEpisodeFO();

        final Episode episode = converter.convert(episodeFO, Episode.class);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for episode.
     */
    @Test
    public void convertEpisodeFO_NullEpisodeFO() {
        assertThat(converter.convert(null, Episode.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertEpisode() {
        final Episode episode = EpisodeUtils.getEpisode();

        final EpisodeFO episodeFO = converter.convert(episode, EpisodeFO.class);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episode);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null episode.
     */
    @Test
    public void convertEpisode_NullEpisode() {
        assertThat(converter.convert(null, EpisodeFO.class), is(nullValue()));
    }

}
