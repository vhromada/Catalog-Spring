package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.facade.to.MusicTO;
import cz.vhromada.catalog.web.fo.MusicFO;

/**
 * A class represents utility class for musics.
 *
 * @author Vladimir Hromada
 */
public final class MusicUtils {

    /**
     * Creates a new instance of MusicUtils.
     */
    private MusicUtils() {
    }

    /**
     * Returns FO for music.
     *
     * @return FO for music
     */
    public static MusicFO getMusicFO() {
        final MusicFO music = new MusicFO();
        music.setId(CatalogUtils.ID);
        music.setName(CatalogUtils.NAME);
        music.setWikiEn(CatalogUtils.EN_WIKI);
        music.setWikiCz(CatalogUtils.CZ_WIKI);
        music.setMediaCount(Integer.toString(CatalogUtils.MEDIA));
        music.setNote(CatalogUtils.NOTE);
        music.setPosition(CatalogUtils.POSITION);

        return music;
    }

    /**
     * Returns TO for music.
     *
     * @return TO for music
     */
    public static MusicTO getMusicTO() {
        final MusicTO music = new MusicTO();
        music.setId(CatalogUtils.ID);
        music.setName(CatalogUtils.NAME);
        music.setWikiEn(CatalogUtils.EN_WIKI);
        music.setWikiCz(CatalogUtils.CZ_WIKI);
        music.setMediaCount(CatalogUtils.MEDIA);
        music.setNote(CatalogUtils.NOTE);
        music.setPosition(CatalogUtils.POSITION);

        return music;
    }

    /**
     * Asserts music deep equals.
     *
     * @param expected expected FO for music
     * @param actual   actual TO for music
     */
    public static void assertMusicDeepEquals(final MusicFO expected, final MusicTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getMediaCount());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getMediaCount(), Integer.toString(actual.getMediaCount()));
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
