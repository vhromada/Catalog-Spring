package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.MusicTO;
import cz.vhromada.catalog.web.commons.MusicUtils;
import cz.vhromada.catalog.web.fo.MusicFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link MusicFO} to {@link MusicTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class MusicConverterTest {

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
    public void testConvertMusicFO() {
        final MusicFO musicFO = MusicUtils.getMusicFO();

        final MusicTO musicTO = converter.convert(musicFO, MusicTO.class);

        MusicUtils.assertMusicDeepEquals(musicFO, musicTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertMusicFO_NullArgument() {
        assertNull(converter.convert(null, MusicTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertMusicTO() {
        final MusicTO musicTO = MusicUtils.getMusicTO();

        final MusicFO musicFO = converter.convert(musicTO, MusicFO.class);

        MusicUtils.assertMusicDeepEquals(musicFO, musicTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertMusicTO_NullArgument() {
        assertNull(converter.convert(null, MusicFO.class));
    }

}
