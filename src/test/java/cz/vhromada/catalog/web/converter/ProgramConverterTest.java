package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.web.common.ProgramUtils;
import cz.vhromada.catalog.web.fo.ProgramFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link ProgramFO} to {@link Program}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class ProgramConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertProgramFO() {
        final ProgramFO programFO = ProgramUtils.getProgramFO();

        final Program program = converter.convert(programFO, Program.class);

        ProgramUtils.assertProgramDeepEquals(programFO, program);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for program.
     */
    @Test
    void convertProgramFO_NullProgramFO() {
        assertThat(converter.convert(null, Program.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertProgram() {
        final Program program = ProgramUtils.getProgram();

        final ProgramFO programFO = converter.convert(program, ProgramFO.class);

        ProgramUtils.assertProgramDeepEquals(programFO, program);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null program.
     */
    @Test
    void convertProgram_NullProgram() {
        assertThat(converter.convert(null, ProgramFO.class)).isNull();
    }

}
