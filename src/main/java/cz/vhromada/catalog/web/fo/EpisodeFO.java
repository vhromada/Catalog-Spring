package cz.vhromada.catalog.web.fo;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * A class represents FO for episode.
 *
 * @author Vladimir Hromada
 */
public class EpisodeFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;

    /**
     * Number of episode
     */
    //CHECKSTYLE.OFF: MagicNumber
    @Range(min = 1, max = 500)
    private String number;
    //CHECKSTYLE.ON: MagicNumber

    /**
     * Name
     */
    @NotBlank
    private String name;

    /**
     * Length
     */
    @Valid
    private TimeFO length;

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
     * Returns number of episode.
     *
     * @return number of episode
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets a new value to number of episode.
     *
     * @param number new value
     */
    public void setNumber(final String number) {
        this.number = number;
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
     * Returns length.
     *
     * @return length
     */
    public TimeFO getLength() {
        return length;
    }

    /**
     * Sets a new value to length.
     *
     * @param length new value
     */
    public void setLength(final TimeFO length) {
        this.length = length;
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
        if (!(obj instanceof EpisodeFO) || id == null) {
            return false;
        }

        return id.equals(((EpisodeFO) obj).id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("EpisodeFO [id=%d, number=%s, name=%s, length=%s, note=%s, position=%d]", id, number, name, length, note, position);
    }

}

