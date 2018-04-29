package cz.vhromada.catalog.web.domain;

import java.io.Serializable;
import java.util.Objects;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.common.Time;

/**
 * A class represents music data.
 *
 * @author Vladimir Hromada
 */
public class MusicData implements Serializable {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Music
     */
    private Music music;

    /**
     * Count of songs
     */
    private int songsCount;

    /**
     * Total length
     */
    private Time totalLength;

    /**
     * Returns music.
     *
     * @return music
     */
    public Music getMusic() {
        return music;
    }

    /**
     * Sets a new value to music.
     *
     * @param music new value
     */
    public void setMusic(final Music music) {
        this.music = music;
    }

    /**
     * Returns count of songs.
     *
     * @return count of songs
     */
    public int getSongsCount() {
        return songsCount;
    }

    /**
     * Sets a new value to count of songs.
     *
     * @param songsCount new value
     */
    public void setSongsCount(final int songsCount) {
        this.songsCount = songsCount;
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
        if (!(obj instanceof MusicData)) {
            return false;
        }

        return Objects.equals(music, ((MusicData) obj).music);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(music);
    }

    @Override
    public String toString() {
        return String.format("MusicData [music=%s, songsCount=%d, totalLength=%s]", music, songsCount, totalLength);
    }

}
