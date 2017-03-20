package cz.vhromada.catalog.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.web.common.ProgramUtils;
import cz.vhromada.catalog.web.fo.ProgramFO;
import cz.vhromada.converter.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link ProgramFO} to {@link Program}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class ProgramConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertProgramFO() {
        final ProgramFO programFO = ProgramUtils.getProgramFO();

        final Program program = converter.convert(programFO, Program.class);

        ProgramUtils.assertProgramDeepEquals(programFO, program);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for program.
     */
    @Test
    public void convertProgramFO_NullProgramFO() {
        assertThat(converter.convert(null, Program.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertProgram() {
        final Program program = ProgramUtils.getProgram();

        final ProgramFO programFO = converter.convert(program, ProgramFO.class);

        ProgramUtils.assertProgramDeepEquals(programFO, program);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null program.
     */
    @Test
    public void convertProgram_NullProgram() {
        assertThat(converter.convert(null, ProgramFO.class), is(nullValue()));
    }

}
