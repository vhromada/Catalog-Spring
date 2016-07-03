package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.ProgramTO;
import cz.vhromada.catalog.web.commons.ProgramUtils;
import cz.vhromada.catalog.web.fo.ProgramFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link ProgramFO} to {@link ProgramTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class ProgramConverterTest {

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
    public void testConvertProgramFO() {
        final ProgramFO programFO = ProgramUtils.getProgramFO();

        final ProgramTO programTO = converter.convert(programFO, ProgramTO.class);

        ProgramUtils.assertProgramDeepEquals(programFO, programTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertProgramFO_NullArgument() {
        assertNull(converter.convert(null, ProgramTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertProgramTO() {
        final ProgramTO programTO = ProgramUtils.getProgramTO();

        final ProgramFO programFO = converter.convert(programTO, ProgramFO.class);

        ProgramUtils.assertProgramDeepEquals(programFO, programTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertProgramTO_NullArgument() {
        assertNull(converter.convert(null, ProgramFO.class));
    }

}
