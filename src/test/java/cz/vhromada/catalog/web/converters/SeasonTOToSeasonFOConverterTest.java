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
 * A class represents test for converter from {@link SeasonTO} to {@link SeasonFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class SeasonTOToSeasonFOConverterTest extends ObjectGeneratorTest {

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
        final SeasonTO seasonTO = objectGenerator.generate(SeasonTO.class);
        final SeasonFO seasonFO = converter.convert(seasonTO, SeasonFO.class);
        DeepAsserts.assertNotNull(seasonFO);
        DeepAsserts.assertEquals(seasonTO, seasonFO, "number", "startYear", "endYear", "show");
        DeepAsserts.assertEquals(Integer.toString(seasonTO.getNumber()), seasonFO.getNumber());
        DeepAsserts.assertEquals(Integer.toString(seasonTO.getStartYear()), seasonFO.getStartYear());
        DeepAsserts.assertEquals(Integer.toString(seasonTO.getEndYear()), seasonFO.getEndYear());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, SeasonFO.class));
    }

}
