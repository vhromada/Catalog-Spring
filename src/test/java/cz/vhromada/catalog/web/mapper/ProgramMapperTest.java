package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.web.common.ProgramUtils;
import cz.vhromada.catalog.web.fo.ProgramFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Program} and {@link ProgramFO}.
 *
 * @author Vladimir Hromada
 */
class ProgramMapperTest {

    /**
     * Mapper for programs
     */
    private ProgramMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ProgramMapper.class);
    }

    /**
     * Test method for {@link ProgramMapper#map(Program)}.
     */
    @Test
    void map() {
        final Program program = ProgramUtils.getProgram();

        final ProgramFO programFO = mapper.map(program);

        ProgramUtils.assertProgramDeepEquals(programFO, program);
    }

    /**
     * Test method for {@link ProgramMapper#map(Program)} with null program.
     */
    @Test
    void map_NullProgram() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link ProgramMapper#mapBack(ProgramFO)}.
     */
    @Test
    void mapBack() {
        final ProgramFO programFO = ProgramUtils.getProgramFO();

        final Program program = mapper.mapBack(programFO);

        ProgramUtils.assertProgramDeepEquals(programFO, program);
    }

    /**
     * Test method for {@link ProgramMapper#mapBack(ProgramFO)} with null program.
     */
    @Test
    void mapBack_NullProgram() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
