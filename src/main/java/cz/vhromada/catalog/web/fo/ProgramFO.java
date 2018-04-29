package cz.vhromada.catalog.web.fo;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

/**
 * A class represents FO for program.
 *
 * @author Vladimir Hromada
 */
public class ProgramFO implements Serializable {

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
     * URL to english Wikipedia page about program
     */
    private String wikiEn;

    /**
     * URL to czech Wikipedia page about program
     */
    private String wikiCz;

    /**
     * Count of media
     */
    //CHECKSTYLE.OFF: MagicNumber
    private @Range(min = 1, max = 100) String mediaCount;
    //CHECKSTYLE.ON: MagicNumber

    /**
     * True if there is crack
     */
    private boolean crack;

    /**
     * True if there is serial key
     */
    private boolean serialKey;

    /**
     * Other data
     */
    private String otherData;

    /**
     * Note
     */
    private String note;

    /**
     * Position
     */
    private Integer position;

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
     * Returns URL to english Wikipedia page about program.
     *
     * @return URL to english Wikipedia page about program
     */
    public String getWikiEn() {
        return wikiEn;
    }

    /**
     * Sets a new value to URL to english Wikipedia page about program.
     *
     * @param wikiEn new value
     */
    public void setWikiEn(final String wikiEn) {
        this.wikiEn = wikiEn;
    }

    /**
     * Returns URL to czech Wikipedia page about program.
     *
     * @return URL to czech Wikipedia page about program
     */
    public String getWikiCz() {
        return wikiCz;
    }

    /**
     * Sets a new value to URL to czech Wikipedia page about program.
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
     * Returns true if there is crack.
     *
     * @return true if there is crack
     */
    public boolean getCrack() {
        return crack;
    }

    /**
     * Sets a new value to if there is crack.
     *
     * @param crack new value
     */
    public void setCrack(final boolean crack) {
        this.crack = crack;
    }

    /**
     * Returns true if there is serial key.
     *
     * @return true if there is serial key
     */
    public boolean getSerialKey() {
        return serialKey;
    }

    /**
     * Sets a new value to if there is serial key.
     *
     * @param serialKey new value
     */
    public void setSerialKey(final boolean serialKey) {
        this.serialKey = serialKey;
    }

    /**
     * Returns other data.
     *
     * @return other data
     */
    public String getOtherData() {
        return otherData;
    }

    /**
     * Sets a new value to other data.
     *
     * @param otherData new value
     */
    public void setOtherData(final String otherData) {
        this.otherData = otherData;
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
    public Integer getPosition() {
        return position;
    }

    /**
     * Sets a new value to position.
     *
     * @param position new value
     */
    public void setPosition(final Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ProgramFO) || id == null) {
            return false;
        }

        return id.equals(((ProgramFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("ProgramFO [id=%d, name=%s, wikiEn=%s, wikiCz=%s, mediaCount=%s, crack=%b, serialKey=%b, otherData=%s, note=%s, position=%d]", id,
            name, wikiEn, wikiCz, mediaCount, crack, serialKey, otherData, note, position);
    }

}
