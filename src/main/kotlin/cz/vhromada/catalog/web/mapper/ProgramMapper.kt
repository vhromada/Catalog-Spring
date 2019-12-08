package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.web.fo.ProgramFO

/**
 * An interface represents mapper for programs.
 *
 * @author Vladimir Hromada
 */
interface ProgramMapper {

    /**
     * Returns FO for program.
     *
     * @param source program
     * @return FO for program
     */
    fun map(source: Program): ProgramFO

    /**
     * Returns program.
     *
     * @param source FO for program
     * @return program
     */
    fun mapBack(source: ProgramFO): Program

}
