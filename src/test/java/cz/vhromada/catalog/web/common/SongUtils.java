package cz.vhromada.catalog.web.common;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.web.fo.SongFO;

/**
 * A class represents utility class for songs.
 *
 * @author Vladimir Hromada
 */
public final class SongUtils {

    /**
     * Creates a new instance of SongUtils.
     */
    private SongUtils() {
    }

    /**
     * Returns FO for song.
     *
     * @return FO for song
     */
    public static SongFO getSongFO() {
        final SongFO song = new SongFO();
        song.setId(CatalogUtils.ID);
        song.setName(CatalogUtils.NAME);
        song.setLength(TimeUtils.getTimeFO());
        song.setNote(CatalogUtils.NOTE);
        song.setPosition(CatalogUtils.POSITION);

        return song;
    }

    /**
     * Returns song.
     *
     * @return song
     */
    public static Song getSong() {
        final Song song = new Song();
        song.setId(CatalogUtils.ID);
        song.setName(CatalogUtils.NAME);
        song.setLength(CatalogUtils.LENGTH);
        song.setNote(CatalogUtils.NOTE);
        song.setPosition(CatalogUtils.POSITION);

        return song;
    }

    /**
     * Asserts song deep equals.
     *
     * @param expected expected FO for song
     * @param actual   actual song
     */
    public static void assertSongDeepEquals(final SongFO expected, final Song actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getName()).isEqualTo(expected.getName());
            TimeUtils.assertTimeDeepEquals(expected.getLength(), actual.getLength());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

}
