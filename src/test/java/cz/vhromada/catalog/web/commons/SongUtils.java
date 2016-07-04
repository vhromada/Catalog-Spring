package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.facade.to.SongTO;
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
     * Returns TO for song.
     *
     * @return TO for song
     */
    public static SongTO getSongTO() {
        final SongTO song = new SongTO();
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
     * @param actual   actual TO for song
     */
    public static void assertSongDeepEquals(final SongFO expected, final SongTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getLength());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        TimeUtils.assertTimeDeepEquals(expected.getLength(), actual.getLength());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
