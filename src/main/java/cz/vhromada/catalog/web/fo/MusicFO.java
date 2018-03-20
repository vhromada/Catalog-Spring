package cz.vhromada.catalog.web.fo;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

/**
 * A class represents FO for music.
 *
 * @author Vladimir Hromada
 */
public class MusicFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Name
     */
    private @NotBlank String name;

    /**
     * URL to english Wikipedia page about music
     */
    private String wikiEn;

    /**
     * URL to czech Wikipedia page about music
     */
    private String wikiCz;

    /**
     * Count of media
     */
    //CHECKSTYLE.OFF: MagicNumber
    private @Range(min = 1, max = 100) String mediaCount;
    //CHECKSTYLE.ON: MagicNumber

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
     * Returns name.
     *
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * Sets a new value to name.
     *
     * @param name new value
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Returns URL to english Wikipedia page about music.
     *
     * @return URL to english Wikipedia page about music
     */
    public String getWikiEn() {
        return wikiEn;
    }

    /**
     * Sets a new value to URL to english Wikipedia page about music.
     *
     * @param wikiEn new value
     */
    public void setWikiEn(final String wikiEn) {
        this.wikiEn = wikiEn;
    }

    /**
     * Returns URL to czech Wikipedia page about music.
     *
     * @return URL to czech Wikipedia page about music
     */
    public String getWikiCz() {
        return wikiCz;
    }

    /**
     * Sets a new value to URL to czech Wikipedia page about music.
     *
     * @param wikiCz new value
     */
    public void setWikiCz(final String wikiCz) {
        this.wikiCz = wikiCz;
    }

    /**
     * Returns count of media.
     *
     * @return count of media
     */
    public String getMediaCount() {
        return mediaCount;
    }

    /**
     * Sets a new value to count of media.
     *
     * @param mediaCount new value
     */
    public void setMediaCount(final String mediaCount) {
        this.mediaCount = mediaCount;
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
        if (!(obj instanceof MusicFO) || id == null) {
            return false;
        }

        return id.equals(((MusicFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("MusicFO [id=%d, name=%s, wikiEn=%s, wikiCz=%s, mediaCount=%s, note=%s, position=%d]", id, name, wikiEn, wikiCz, mediaCount, note,
            position);
    }

}
