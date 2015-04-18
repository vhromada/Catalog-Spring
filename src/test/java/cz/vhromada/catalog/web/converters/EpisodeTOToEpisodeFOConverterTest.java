package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.EpisodeTO;
import cz.vhromada.catalog.web.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.web.fo.EpisodeFO;
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
 * A class represents test for converter from {@link EpisodeTO} to {@link EpisodeFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class EpisodeTOToEpisodeFOConverterTest extends ObjectGeneratorTest {

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
        final EpisodeTO episodeTO = objectGenerator.generate(EpisodeTO.class);

        final EpisodeFO episodeFO = converter.convert(episodeTO, EpisodeFO.class);

        DeepAsserts.assertNotNull(episodeFO);
        DeepAsserts.assertEquals(episodeTO, episodeFO, "number", "length", "season");
        DeepAsserts.assertEquals(Integer.toString(episodeTO.getNumber()), episodeFO.getNumber());
        final Time length = new Time(episodeTO.getLength());
        DeepAsserts.assertEquals(length, episodeFO.getLength(), "length", "hours", "minutes", "seconds");
        DeepAsserts.assertEquals(Integer.toString(length.getData(Time.TimeData.HOUR)), episodeFO.getLength().getHours());
        DeepAsserts.assertEquals(Integer.toString(length.getData(Time.TimeData.MINUTE)), episodeFO.getLength().getMinutes());
        DeepAsserts.assertEquals(Integer.toString(length.getData(Time.TimeData.SECOND)), episodeFO.getLength().getSeconds());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, EpisodeFO.class));
    }

}
