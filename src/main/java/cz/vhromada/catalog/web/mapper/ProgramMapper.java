package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.web.fo.ProgramFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for programs.
 *
 * @author Vladimir Hromada
 */
@Mapper
public interface ProgramMapper {

    /**
     * Returns FO for program.
     *
     * @param source program
     * @return FO for program
     */
    ProgramFO map(Program source);

    /**
     * Returns program.
     *
     * @param source FO for program
     * @return program
     */
    Program mapBack(ProgramFO source);

}
