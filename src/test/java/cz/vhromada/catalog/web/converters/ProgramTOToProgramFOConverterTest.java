package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.facade.to.ProgramTO;
import cz.vhromada.catalog.web.fo.ProgramFO;
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
 * A class represents test for converter from {@link ProgramTO} to {@link ProgramFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class ProgramTOToProgramFOConverterTest extends ObjectGeneratorTest {

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
        final ProgramTO programTO = objectGenerator.generate(ProgramTO.class);
        final ProgramFO programFO = converter.convert(programTO, ProgramFO.class);
        DeepAsserts.assertNotNull(programFO);
        DeepAsserts.assertEquals(programTO, programFO, "mediaCount");
        DeepAsserts.assertEquals(String.valueOf(programTO.getMediaCount()), programFO.getMediaCount());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, ProgramFO.class));
    }

}
