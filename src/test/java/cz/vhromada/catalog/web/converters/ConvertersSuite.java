package cz.vhromada.catalog.web.converters;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A class represents test suite for package cz.vhromada.catalog.web.converters.
 *
 * @author Vladimir Hromada
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ ShowTOToShowFOConverterTest.class, ShowFOToShowTOConverterTest.class, ShowTOToShowConverterTest.class, ShowToShowTOConverterTest.class,
        SeasonTOToSeasonFOConverterTest.class, SeasonFOToSeasonTOConverterTest.class, SeasonTOToSeasonConverterTest.class, SeasonToSeasonTOConverterTest.class,
        GameTOToGameFOConverterTest.class, GameFOToGameTOConverterTest.class, MusicTOToMusicFOConverterTest.class,
        MusicFOToMusicTOConverterTest.class, MusicTOToMusicConverterTest.class, MusicToMusicTOConverterTest.class, SongTOToSongFOConverterTest.class,
        SongFOToSongTOConverterTest.class, ProgramTOToProgramFOConverterTest.class, ProgramFOToProgramTOConverterTest.class,
        BookCategoryTOToBookCategoryFOConverterTest.class, BookCategoryFOToBookCategoryTOConverterTest.class, BookCategoryTOToBookCategoryConverterTest.class,
        BookCategoryToBookCategoryTOConverterTest.class, BookTOToBookFOConverterTest.class, BookFOToBookTOConverterTest.class,
        GenreTOToGenreFOConverterTest.class, GenreFOToGenreTOConverterTest.class, GenreTOToStringConverterTest.class, StringToGenreTOConverterTest.class })
public class ConvertersSuite {
}
