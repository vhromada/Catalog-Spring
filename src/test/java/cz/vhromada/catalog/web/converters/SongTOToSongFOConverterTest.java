package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.SongTO;
import cz.vhromada.catalog.web.commons.ObjectGeneratorTest;
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
 * A class represents test for converter from {@link SongTO} to {@link SongFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class SongTOToSongFOConverterTest extends ObjectGeneratorTest {

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
        final SongTO songTO = objectGenerator.generate(SongTO.class);

        final SongFO songFO = converter.convert(songTO, SongFO.class);

        DeepAsserts.assertNotNull(songFO);
        DeepAsserts.assertEquals(songTO, songFO, "length", "music");
        final Time length = new Time(songTO.getLength());
        DeepAsserts.assertEquals(length, songFO.getLength(), "length", "hours", "minutes", "seconds");
        DeepAsserts.assertEquals(Integer.toString(length.getData(Time.TimeData.HOUR)), songFO.getLength().getHours());
        DeepAsserts.assertEquals(Integer.toString(length.getData(Time.TimeData.MINUTE)), songFO.getLength().getMinutes());
        DeepAsserts.assertEquals(Integer.toString(length.getData(Time.TimeData.SECOND)), songFO.getLength().getSeconds());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, SongFO.class));
    }

}
