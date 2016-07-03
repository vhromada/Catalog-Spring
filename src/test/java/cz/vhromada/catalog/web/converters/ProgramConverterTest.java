package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.ProgramTO;
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
        final ProgramFO programFO = getProgramFO();

        final ProgramTO programTO = converter.convert(programFO, ProgramTO.class);

        assertProgramDeepEquals(programTO, programFO);
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
        final ProgramTO programTO = getProgramTO();

        final ProgramFO programFO = converter.convert(programTO, ProgramFO.class);

        assertProgramDeepEquals(programTO, programFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertProgramTO_NullArgument() {
        assertNull(converter.convert(null, ProgramFO.class));
    }

    /**
     * Returns FO for program.
     *
     * @return FO for program
     */
    private static ProgramFO getProgramFO() {
        final ProgramFO program = new ProgramFO();
        program.setId(1);
        program.setName("Name");
        program.setWikiEn("enWiki");
        program.setWikiCz("czWiki");
        program.setMediaCount("1");
        program.setCrack(true);
        program.setSerialKey(true);
        program.setOtherData("Other data");
        program.setNote("Note");

        return program;
    }

    /**
     * Returns TO for program.
     *
     * @return TO for program
     */
    private static ProgramTO getProgramTO() {
        final ProgramTO program = new ProgramTO();
        program.setId(1);
        program.setName("Name");
        program.setWikiEn("enWiki");
        program.setWikiCz("czWiki");
        program.setMediaCount(1);
        program.setCrack(true);
        program.setSerialKey(true);
        program.setOtherData("Other data");
        program.setNote("Note");

        return program;
    }

    /**
     * Asserts program deep equals.
     *
     * @param expected expected TO for program
     * @param actual   actual program
     */
    public static void assertProgramDeepEquals(final ProgramTO expected, final ProgramFO actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertNotNull(expected.getMediaCount());
        assertNotNull(actual.getMediaCount());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(Integer.toString(expected.getMediaCount()), actual.getMediaCount());
        assertEquals(expected.getCrack(), actual.getCrack());
        assertEquals(expected.getSerialKey(), actual.getSerialKey());
        assertEquals(expected.getOtherData(), actual.getOtherData());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
