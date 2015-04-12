package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.web.fo.SeasonFO;
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
 * A class represents test for converter from {@link SeasonFO} to {@link SeasonTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class SeasonFOToSeasonTOConverterTest extends ObjectGeneratorTest {

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
        final SeasonFO seasonFO = objectGenerator.generate(SeasonFO.class);
        seasonFO.setNumber(Integer.toString(objectGenerator.generate(Integer.class)));
        seasonFO.setStartYear(Integer.toString(objectGenerator.generate(Integer.class)));
        seasonFO.setEndYear(Integer.toString(objectGenerator.generate(Integer.class)));
        final SeasonTO seasonTO = converter.convert(seasonFO, SeasonTO.class);
        DeepAsserts.assertNotNull(seasonTO, "show");
        DeepAsserts.assertEquals(seasonFO, seasonTO, "number", "startYear", "endYear", "show");
        DeepAsserts.assertEquals(Integer.valueOf(seasonFO.getNumber()), seasonTO.getNumber());
        DeepAsserts.assertEquals(Integer.valueOf(seasonFO.getStartYear()), seasonTO.getStartYear());
        DeepAsserts.assertEquals(Integer.valueOf(seasonFO.getEndYear()), seasonTO.getEndYear());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, SeasonTO.class));
    }

}
