package cz.vhromada.catalog.web.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A class represents test suite for package cz.vhromada.catalog.web.converters.
 *
 * @author Vladimir Hromada
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ MovieConverterTest.class, ShowConverterTest.class, SeasonConverterTest.class, EpisodeConverterTest.class, GameConverterTest.class,
        MusicConverterTest.class, SongConverterTest.class, ProgramConverterTest.class, GenreConverterTest.class, GenreToIntegerConverterTest.class,
        TimeConverterTest.class })
public class ConvertersSuite {
}
