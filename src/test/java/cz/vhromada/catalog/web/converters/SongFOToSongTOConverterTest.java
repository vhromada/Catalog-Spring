package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.SongTO;
import cz.vhromada.catalog.web.fo.SongFO;
import cz.vhromada.converters.Converter;
import cz.vhromada.generator.ObjectGenerator;
import cz.vhromada.test.DeepAsserts;

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
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class SongFOToSongTOConverterTest extends ObjectGeneratorTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Instance of {@link ObjectGenerator}
     */
    @Autowired
    private ObjectGenerator objectGenerator;

    /**
     * Test method for {@link Converter#convert(Object, Class)}.
     */
    @Test
    public void testConvert() {
        final SongFO songFO = objectGenerator.generate(SongFO.class);
        final Time time = objectGenerator.generate(Time.class);
        songFO.getLength().setHours(Integer.toString(time.getData(Time.TimeData.HOUR)));
        songFO.getLength().setMinutes(Integer.toString(time.getData(Time.TimeData.MINUTE)));
        songFO.getLength().setSeconds(Integer.toString(time.getData(Time.TimeData.SECOND)));

        final SongTO songTO = converter.convert(songFO, SongTO.class);

        DeepAsserts.assertNotNull(songTO, "music");
        DeepAsserts.assertEquals(songFO, songTO, "length", "music");
        DeepAsserts.assertEquals(time.getLength(), songTO.getLength());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, SongTO.class));
    }

}
