package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.EpisodeTO;
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
 * A class represents test for converter from {@link EpisodeFO} to {@link EpisodeTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class EpisodeFOToEpisodeTOConverterTest extends ObjectGeneratorTest {

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
        final EpisodeFO episodeFO = objectGenerator.generate(EpisodeFO.class);
        episodeFO.setNumber(String.valueOf(objectGenerator.generate(Integer.class)));
        final Time time = objectGenerator.generate(Time.class);
        episodeFO.setHours(Integer.toString(time.getData(Time.TimeData.HOUR)));
        episodeFO.setMinutes(Integer.toString(time.getData(Time.TimeData.MINUTE)));
        episodeFO.setSeconds(Integer.toString(time.getData(Time.TimeData.SECOND)));
        final EpisodeTO episodeTO = converter.convert(episodeFO, EpisodeTO.class);
        DeepAsserts.assertNotNull(episodeTO, "season");
        DeepAsserts.assertEquals(episodeFO, episodeTO, "number", "hours", "minutes", "seconds", "length", "season");
        DeepAsserts.assertEquals(String.valueOf(episodeTO.getNumber()), episodeFO.getNumber());
        DeepAsserts.assertEquals(time.getLength(), episodeTO.getLength());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, EpisodeTO.class));
    }

}
