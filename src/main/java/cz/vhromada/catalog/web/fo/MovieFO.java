package cz.vhromada.catalog.web.fo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import cz.vhromada.catalog.common.Language;
import cz.vhromada.catalog.utils.Constants;
import cz.vhromada.catalog.web.validator.constraints.DateRange;
import cz.vhromada.catalog.web.validator.constraints.Imdb;
import cz.vhromada.catalog.web.validator.constraints.ImdbCode;

/**
 * A class represents FO for movie.
 *
 * @author Vladimir Hromada
 */
@Imdb
public class MovieFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Czech name
     */
    private @NotBlank String czechName;

    /**
     * Original name
     */
    private @NotBlank String originalName;

    /**
     * Year
     */
    @DateRange(Constants.MIN_YEAR)
    private String year;

    /**
     * Language
     */
    private @NotNull Language language;

    /**
     * Subtitles
     */
    private List<Language> subtitles;

    /**
     * Media
     */
    private @Valid List<TimeFO> media;

    /**
     * URL to ČSFD page about movie
     */
    private String csfd;

    /**
     * True if IMDB is selected
     */
    private boolean imdb;

    /**
     * IMDB code
     */
    @ImdbCode
    private String imdbCode;

    /**
     * URL to english Wikipedia page about movie
     */
    private String wikiEn;

    /**
     * URL to czech Wikipedia page about movie
     */
    private String wikiCz;

    /**
     * Path to file with movie's picture
     */
    private String picture;

    /**
     * Note
     */
    private String note;

    /**
     * Position
     */
    private int position;

    /**
     * Genres
     */
    private @NotNull @Size(min = 1) List<Integer> genres;

    /**
     * Creates a new instance of MovieFO.
     */
    public MovieFO() {
        media = new ArrayList<>();
        media.add(new TimeFO());
    }

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
     * Returns czech name.
     *
     * @return czech name
     */
    public String getCzechName() {
        return czechName;
    }

    /**
     * Sets a new value to czech name.
     *
     * @param czechName new value
     */
    public void setCzechName(final String czechName) {
        this.czechName = czechName;
    }

    /**
     * Returns original name.
     *
     * @return original name
     */
    public String getOriginalName() {
        return originalName;
    }

    /**
     * Sets a new value to original name.
     *
     * @param originalName new value
     */
    public void setOriginalName(final String originalName) {
        this.originalName = originalName;
    }

    /**
     * Returns year.
     *
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * Sets a new value to year.
     *
     * @param year new value
     */
    public void setYear(final String year) {
        this.year = year;
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
     * Returns media.
     *
     * @return media
     */
    public List<TimeFO> getMedia() {
        return media;
    }

    /**
     * Sets a new value to media.
     *
     * @param media new value
     */
    public void setMedia(final List<TimeFO> media) {
        this.media = media;
    }

    /**
     * Returns URL to ČSFD page about movie.
     *
     * @return URL to ČSFD page about movie
     */
    public String getCsfd() {
        return csfd;
    }

    /**
     * Sets a new value to URL to ČSFD page about movie.
     *
     * @param csfd new value
     */
    public void setCsfd(final String csfd) {
        this.csfd = csfd;
    }

    /**
     * Returns true if IMDB is selected.
     *
     * @return true if IMDB is selected
     */
    public boolean getImdb() {
        return imdb;
    }

    /**
     * Sets a new value to true if IMDB is selected.
     *
     * @param imdb new value
     */
    public void setImdb(final boolean imdb) {
        this.imdb = imdb;
    }

    /**
     * Returns IMDB code.
     *
     * @return IMDB code
     */
    public String getImdbCode() {
        return imdbCode;
    }

    /**
     * Sets a new value to IMDB code.
     *
     * @param imdbCode new value
     */
    public void setImdbCode(final String imdbCode) {
        this.imdbCode = imdbCode;
    }

    /**
     * Returns URL to english Wikipedia page about movie.
     *
     * @return URL to english Wikipedia page about movie
     */
    public String getWikiEn() {
        return wikiEn;
    }

    /**
     * Sets a new value to URL to english Wikipedia page about movie.
     *
     * @param wikiEn new value
     */
    public void setWikiEn(final String wikiEn) {
        this.wikiEn = wikiEn;
    }

    /**
     * Returns URL to czech Wikipedia page about movie.
     *
     * @return URL to czech Wikipedia page about movie
     */
    public String getWikiCz() {
        return wikiCz;
    }

    /**
     * Sets a new value to URL to czech Wikipedia page about movie.
     *
     * @param wikiCz new value
     */
    public void setWikiCz(final String wikiCz) {
        this.wikiCz = wikiCz;
    }

    /**
     * Returns path to file with movie's picture.
     *
     * @return path to file with movie's picture
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets a new value to path to file with movie's picture.
     *
     * @param picture new value
     */
    public void setPicture(final String picture) {
        this.picture = picture;
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

    /**
     * Returns genres.
     *
     * @return genres
     */
    public List<Integer> getGenres() {
        return genres;
    }

    /**
     * Sets a new value to genres.
     *
     * @param genres new value
     */
    public void setGenres(final List<Integer> genres) {
        this.genres = genres;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MovieFO) || id == null) {
            return false;
        }

        return id.equals(((MovieFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("MovieFO [id=%d, czechName=%s, originalName=%s, year=%s, language=%s, subtitles=%s, media=%s, csfd=%s, imdb=%b, imdbCode=%s, "
                        + "wikiEn=%s, wikiCz=%s, picture=%s, note=%s, position=%d, genres=%s]", id, czechName, originalName, year, language, subtitles, media,
                csfd, imdb, imdbCode, wikiEn, wikiCz, picture, note, position, genres);
    }

}
