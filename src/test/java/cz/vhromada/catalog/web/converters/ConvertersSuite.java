package cz.vhromada.catalog.web.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A class represents test suite for package cz.vhromada.catalog.web.converters.
 *
 * @author Vladimir Hromada
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ GameTOToGameFOConverterTest.class, GameFOToGameTOConverterTest.class, ProgramTOToProgramFOConverterTest.class,
        ProgramFOToProgramTOConverterTest.class })
public class ConvertersSuite {
}
