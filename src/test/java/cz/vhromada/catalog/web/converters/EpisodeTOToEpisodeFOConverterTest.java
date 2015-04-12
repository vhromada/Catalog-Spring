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
        DeepAsserts.assertEquals(episodeTO, episodeFO, "number", "length", "hours", "minutes", "seconds", "season");
        DeepAsserts.assertEquals(String.valueOf(episodeTO.getNumber()), episodeFO.getNumber());
        final Time length = new Time(episodeTO.getLength());
        DeepAsserts.assertEquals(String.valueOf(length.getData(Time.TimeData.HOUR)), episodeFO.getHours());
        DeepAsserts.assertEquals(String.valueOf(length.getData(Time.TimeData.MINUTE)), episodeFO.getMinutes());
        DeepAsserts.assertEquals(String.valueOf(length.getData(Time.TimeData.SECOND)), episodeFO.getSeconds());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, EpisodeFO.class));
    }

}
