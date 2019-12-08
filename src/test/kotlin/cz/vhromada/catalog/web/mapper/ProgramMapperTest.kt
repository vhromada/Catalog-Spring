package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.web.CatalogMapperTestConfiguration
import cz.vhromada.catalog.web.common.ProgramUtils
import cz.vhromada.catalog.web.fo.ProgramFO
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 * A class represents test for mapper between [Program] and [ProgramFO].
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [CatalogMapperTestConfiguration::class])
class ProgramMapperTest {

    /**
     * Mapper for programs
     */
    @Autowired
    private lateinit var mapper: ProgramMapper

    /**
     * Test method for [ProgramMapper.map].
     */
    @Test
    fun map() {
        val program = ProgramUtils.getProgram()

        val programFO = mapper.map(program)

        ProgramUtils.assertProgramDeepEquals(programFO, program)
    }

    /**
     * Test method for [ProgramMapper.mapBack].
     */
    @Test
    fun mapBack() {
        val programFO = ProgramUtils.getProgramFO()

        val program = mapper.mapBack(programFO)

        ProgramUtils.assertProgramDeepEquals(programFO, program)
    }

}
