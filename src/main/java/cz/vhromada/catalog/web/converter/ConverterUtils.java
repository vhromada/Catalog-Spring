package cz.vhromada.catalog.web.converter;

import java.util.List;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Program;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.common.Language;
import cz.vhromada.common.Time;

import org.springframework.util.StringUtils;

/**
 * A class represents utility class for converters.
 *
 * @author Vladimir Hromada
 */
public final class ConverterUtils {

    /**
     * Creates a new instance of ConverterUtils.
     */
    private ConverterUtils() {
    }

    /**
     * Converts length.
     *
     * @param length length
     * @return converted length
     */
    public static String convertLength(final int length) {
        return new Time(length).toString();
    }

    /**
     * Converts languages.
     *
     * @param languages languages
     * @return converted languages
     */
    public static String convertLanguages(final List<Language> languages) {
        if (languages.isEmpty()) {
            return "";
        }

        final StringBuilder result = new StringBuilder();
        for (final Language language : languages) {
            result.append(language);
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    /**
     * Converts movie's media.
     *
     * @param media media
     * @return converted movie's media
     */
    public static String convertMedia(final List<Medium> media) {
        final StringBuilder result = new StringBuilder();
        for (final Medium medium : media) {
            result.append(new Time(medium.getLength()));
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    /**
     * Converts movie's total length.
     *
     * @param media media
     * @return converted movie's total length
     */
    public static String convertMovieTotalLength(final List<Medium> media) {
        int totalLength = 0;
        for (final Medium medium : media) {
            totalLength += medium.getLength();
        }

        return new Time(totalLength).toString();
    }

    /**
     * Converts game's additional data.
     *
     * @param game game
     * @return converted game's additional data
     */
    public static String convertGameAdditionalData(final Game game) {
        final StringBuilder result = new StringBuilder();
        if (game.getCrack()) {
            result.append("Crack");
        }
        addToResult(result, game.getSerialKey(), "serial key");
        addToResult(result, game.getPatch(), "patch");
        addToResult(result, game.getTrainer(), "trainer");
        addToResult(result, game.getTrainerData(), "data for trainer");
        addToResult(result, game.getEditor(), "editor");
        addToResult(result, game.getSaves(), "saves");
        if (game.getOtherData() != null && !game.getOtherData().isEmpty()) {
            if (result.length() != 0) {
                result.append(", ");
            }
            result.append(game.getOtherData());
        }

        return result.toString();
    }

    /**
     * Converts content of game's additional data.
     *
     * @param game game
     * @return converted content of game's additional data
     */
    public static boolean convertGameAdditionalDataContent(final Game game) {
        return !StringUtils.isEmpty(convertGameAdditionalData(game));
    }

    /**
     * Converts seasons's years.
     *
     * @param season season
     * @return converted seasons's years
     */
    public static String convertSeasonYears(final Season season) {
        return season.getStartYear() == season.getEndYear() ? Integer.toString(season.getStartYear()) : season.getStartYear() + " - " + season.getEndYear();
    }

    /**
     * Converts program's additional data.
     *
     * @param program program
     * @return converted program's additional data
     */
    public static String convertProgramAdditionalData(final Program program) {
        final StringBuilder result = new StringBuilder();
        if (program.getCrack()) {
            result.append("Crack");
        }
        addToResult(result, program.getSerialKey(), "serial key");
        if (program.getOtherData() != null && !program.getOtherData().isEmpty()) {
            if (result.length() != 0) {
                result.append(", ");
            }
            result.append(program.getOtherData());
        }

        return result.toString();
    }

    /**
     * Converts content of program's additional data.
     *
     * @param program program
     * @return converted content of program's additional data
     */
    public static boolean convertProgramAdditionalDataContent(final Program program) {
        return !StringUtils.isEmpty(convertProgramAdditionalData(program));
    }

    /**
     * Converts genres.
     *
     * @param genres genres
     * @return converted genres
     */
    public static String convertGenres(final List<Genre> genres) {
        final StringBuilder result = new StringBuilder();
        for (final Genre genre : genres) {
            result.append(genre.getName());
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

    /**
     * Adds data to result.
     *
     * @param result result
     * @param value  value
     * @param data   data
     */
    private static void addToResult(final StringBuilder result, final boolean value, final String data) {
        if (value) {
            if (result.length() == 0) {
                result.append(data.substring(0, 1).toUpperCase());
                result.append(data.substring(1));
            } else {
                result.append(", ");
                result.append(data);
            }
        }
    }

}
