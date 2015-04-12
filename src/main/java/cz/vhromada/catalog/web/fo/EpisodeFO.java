package cz.vhromada.catalog.web.fo;

import java.io.Serializable;

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
    @Range(min = 1, max = 500)
    private String number;

    /**
     * Name
     */
    @NotBlank
    private String name;

    /**
     * Hours
     */
    @Range(min = 0, max = 23)
    private String hours;

    /**
     * Minutes
     */
    @Range(min = 0, max = 59)
    private String minutes;

    /**
     * Seconds
     */
    @Range(min = 0, max = 59)
    private String seconds;

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
     * Returns hours.
     *
     * @return hours
     */
    public String getHours() {
        return hours;
    }

    /**
     * Sets a new value to hours.
     *
     * @param hours new value
     */
    public void setHours(final String hours) {
        this.hours = hours;
    }

    /**
     * Returns minutes.
     *
     * @return minutes
     */
    public String getMinutes() {
        return minutes;
    }

    /**
     * Sets a new value to minutes.
     *
     * @param minutes new value
     */
    public void setMinutes(final String minutes) {
        this.minutes = minutes;
    }

    /**
     * Returns seconds.
     *
     * @return seconds
     */
    public String getSeconds() {
        return seconds;
    }

    /**
     * Sets a new value to seconds.
     *
     * @param seconds new value
     */
    public void setSeconds(final String seconds) {
        this.seconds = seconds;
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
        if (obj == null || !(obj instanceof EpisodeFO) || id == null) {
            return false;
        }
        final EpisodeFO episode = (EpisodeFO) obj;
        return id.equals(episode.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("EpisodeFO [id=%d, number=%s, name=%s, hours=%s, minutes=%s, seconds=%s, note=%s, position=%d]", id, number, name, hours, minutes,
                seconds, note, position);
    }

}

