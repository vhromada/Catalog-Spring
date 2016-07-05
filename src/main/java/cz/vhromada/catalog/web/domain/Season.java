package cz.vhromada.catalog.web.domain;

import java.io.Serializable;
import java.util.Objects;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.SeasonTO;

/**
 * A class represents season.
 *
 * @author Vladimir Hromada
 */
public class Season implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * TO for season
     */
    private SeasonTO season;

    /**
     * Count of episodes
     */
    private int episodesCount;

    /**
     * Total length
     */
    private Time totalLength;

    /**
     * Returns TO for season.
     *
     * @return TO for season
     */
    public SeasonTO getSeason() {
        return season;
    }

    /**
     * Sets a new value to TO for season.
     *
     * @param season new value
     */
    public void setSeason(final SeasonTO season) {
        this.season = season;
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
        if (!(obj instanceof Season)) {
            return false;
        }

        return Objects.equals(season, ((Season) obj).season);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(season);
    }

    @Override
    public String toString() {
        return String.format("Season [season=%s, episodesCount=%d, totalLength=%s]", season, episodesCount, totalLength);
    }

}
