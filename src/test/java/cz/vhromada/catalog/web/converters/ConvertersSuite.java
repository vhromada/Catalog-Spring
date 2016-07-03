package cz.vhromada.catalog.web.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A class represents test suite for package cz.vhromada.catalog.web.converters.
 *
 * @author Vladimir Hromada
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ /*MovieTOToMovieFOConverterTest.class, MovieFOToMovieTOConverterTest.class,
        ShowTOToShowFOConverterTest.class, ShowFOToShowTOConverterTest.class, ShowTOToShowConverterTest.class, ShowToShowTOConverterTest.class,
        SeasonTOToSeasonFOConverterTest.class, SeasonFOToSeasonTOConverterTest.class, SeasonTOToSeasonConverterTest.class, SeasonToSeasonTOConverterTest.class,
        EpisodeTOToEpisodeFOConverterTest.class, EpisodeFOToEpisodeTOConverterTest.class, */
        GameConverterTest.class, MusicConverterTest.class, SongConverterTest.class, ProgramConverterTest.class, GenreConverterTest.class,
        /*, GenreTOToStringConverterTest.class, StringToGenreTOConverterTest.class,*/
        TimeConverterTest.class })
public class ConvertersSuite {
}
