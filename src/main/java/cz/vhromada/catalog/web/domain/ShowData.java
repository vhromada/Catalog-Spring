package cz.vhromada.catalog.web.domain;

import java.io.Serializable;
import java.util.Objects;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Show;

/**
 * A class represents show data.
 *
 * @author Vladimir Hromada
 */
public class ShowData implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Show
     */
    private Show show;

    /**
     * Count of seasons
     */
    private int seasonsCount;

    /**
     * Count of episodes
     */
    private int episodesCount;

    /**
     * Total length
     */
    private Time totalLength;

    /**
     * Returns show.
     *
     * @return show
     */
    public Show getShow() {
        return show;
    }

    /**
     * Sets a new value to show.
     *
     * @param show new value
     */
    public void setShow(final Show show) {
        this.show = show;
    }

    /**
     * Returns count of seasons.
     *
     * @return count of seasons
     */
    public int getSeasonsCount() {
        return seasonsCount;
    }

    /**
     * Sets a new value to count of seasons.
     *
     * @param seasonsCount new value
     */
    public void setSeasonsCount(final int seasonsCount) {
        this.seasonsCount = seasonsCount;
    }

    /**
     * Returns count of episodes.
     *
     * @return count of episodes
     */
    public int getEpisodesCount() {
        return episodesCount;
    }

    /**
     * Sets a new value to count of episodes.
     *
     * @param episodesCount new value
     */
    public void setEpisodesCount(final int episodesCount) {
        this.episodesCount = episodesCount;
    }

    /**
     * Returns total length.
     *
     * @return total length
     */
    public Time getTotalLength() {
        return totalLength;
    }

    /**
     * Sets a new value to total length.
     *
     * @param totalLength new value
     */
    public void setTotalLength(final Time totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShowData)) {
            return false;
        }

        return Objects.equals(show, ((ShowData) obj).show);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(show);
    }

    @Override
    public String toString() {
        return String.format("ShowData [show=%s, seasonsCount=%d, episodesCount=%d, totalLength=%s]", show, seasonsCount, episodesCount, totalLength);
    }

}
