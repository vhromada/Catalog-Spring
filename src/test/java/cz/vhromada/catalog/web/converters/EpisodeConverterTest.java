package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.EpisodeTO;
import cz.vhromada.catalog.web.commons.EpisodeUtils;
import cz.vhromada.catalog.web.fo.EpisodeFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link EpisodeFO} to {@link EpisodeTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class EpisodeConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO.
     */
    @Test
    public void testConvertEpisodeFO() {
        final EpisodeFO episodeFO = EpisodeUtils.getEpisodeFO();

        final EpisodeTO episodeTO = converter.convert(episodeFO, EpisodeTO.class);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episodeTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertEpisodeFO_NullArgument() {
        assertNull(converter.convert(null, EpisodeTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertEpisodeTO() {
        final EpisodeTO episodeTO = EpisodeUtils.getEpisodeTO();

        final EpisodeFO episodeFO = converter.convert(episodeTO, EpisodeFO.class);

        EpisodeUtils.assertEpisodeDeepEquals(episodeFO, episodeTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertEpisodeTO_NullArgument() {
        assertNull(converter.convert(null, EpisodeFO.class));
    }

}
