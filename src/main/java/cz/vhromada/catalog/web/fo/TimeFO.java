package cz.vhromada.catalog.web.fo;

import java.io.Serializable;
import java.util.Objects;

import org.hibernate.validator.constraints.Range;

/**
 * A class represents FO for time.
 *
 * @author Vladimir Hromada
 */
public class TimeFO implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

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

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TimeFO)) {
            return false;
        }

        final TimeFO time = (TimeFO) obj;
        return Objects.equals(hours, time.hours) && Objects.equals(minutes, time.minutes) && Objects.equals(seconds, time.seconds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes, seconds);
    }

    @Override
    public String toString() {
        return String.format("TimeFO [hours=%s, minutes=%s, seconds=%s]", hours, minutes, seconds);
    }

}
