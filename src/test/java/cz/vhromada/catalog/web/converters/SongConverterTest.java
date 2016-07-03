package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.SongTO;
import cz.vhromada.catalog.web.commons.SongUtils;
import cz.vhromada.catalog.web.fo.SongFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link SongFO} to {@link SongTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class SongConverterTest {

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
    public void testConvertSongFO() {
        final SongFO songFO = SongUtils.getSongFO();

        final SongTO songTO = converter.convert(songFO, SongTO.class);

        SongUtils.assertSongDeepEquals(songFO, songTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertSongFO_NullArgument() {
        assertNull(converter.convert(null, SongTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertSongTO() {
        final SongTO songTO = SongUtils.getSongTO();

        final SongFO songFO = converter.convert(songTO, SongFO.class);

        SongUtils.assertSongDeepEquals(songFO, songTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertSongTO_NullArgument() {
        assertNull(converter.convert(null, SongFO.class));
    }

}
