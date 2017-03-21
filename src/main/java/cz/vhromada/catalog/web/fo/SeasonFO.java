package cz.vhromada.catalog.web.fo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import cz.vhromada.catalog.common.Language;
import cz.vhromada.catalog.utils.Constants;
import cz.vhromada.catalog.web.validator.constraints.DateRange;
import cz.vhromada.catalog.web.validator.constraints.Years;

import org.hibernate.validator.constraints.Range;

/**
 * A class represents FO for season.
 *
 * @author Vladimir Hromada
 */
@Years
public class SeasonFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Number of season
     */
    //CHECKSTYLE.OFF: MagicNumber
    @Range(min = 1, max = 100)
    private String number;
    //CHECKSTYLE.ON: MagicNumber

    /**
     * Starting year
     */
    @DateRange(Constants.MIN_YEAR)
    private String startYear;

    /**
     * Ending year
     */
    @DateRange(Constants.MIN_YEAR)
    private String endYear;

    /**
     * Language
     */
    @NotNull
    private Language language;

    /**
     * Subtitles
     */
    private List<Language> subtitles;

    /**
     * Note
     */
    private String note;

    /**
     * Position
     */
    private int position;

    /**
     * Returns ID.
     *
     * @return ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets a new value to ID.
     *
     * @param id new value
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Returns number of season.
     *
     * @return number of season
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets a new value to number of season.
     *
     * @param number new value
     */
    public void setNumber(final String number) {
        this.number = number;
    }

    /**
     * Returns starting year.
     *
     * @return starting year
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * Sets a new value to starting year.
     *
     * @param startYear new value
     */
    public void setStartYear(final String startYear) {
        this.startYear = startYear;
    }

    /**
     * Returns ending year.
     *
     * @return ending year
     */
    public String getEndYear() {
        return endYear;
    }

    /**
     * Sets a new value to ending year.
     *
     * @param endYear new value
     */
    public void setEndYear(final String endYear) {
        this.endYear = endYear;
    }

    /**
     * Returns language.
     *
     * @return language
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Sets a new value to language.
     *
     * @param language new value
     * @throws IllegalArgumentException if new value is null
     */
    public void setLanguage(final Language language) {
        this.language = language;
    }

    /**
     * Returns subtitles.
     *
     * @return subtitles
     */
    public List<Language> getSubtitles() {
        return subtitles;
    }

    /**
     * Sets a new value to subtitles.
     *
     * @param subtitles new value
     */
    public void setSubtitles(final List<Language> subtitles) {
        this.subtitles = subtitles;
    }

    /**
     * Returns note.
     *
     * @return note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets a new value to note.
     *
     * @param note new value
     */
    public void setNote(final String note) {
        this.note = note;
    }

    /**
     * Returns position.
     *
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets a new value to position.
     *
     * @param position new value
     */
    public void setPosition(final int position) {
        this.position = position;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeasonFO) || id == null) {
            return false;
        }

        return id.equals(((SeasonFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("SeasonFO [id=%d, number=%s, startYear=%s, endYear=%s, language=%s, subtitles=%s, note=%s, position=%d]", id, number, startYear,
                endYear, language, subtitles, note, position);
    }

}
