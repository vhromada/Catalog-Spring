package cz.vhromada.catalog.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Music;
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
        music.setMediaCount(CatalogUtils.MEDIA.toString());
        music.setNote(CatalogUtils.NOTE);
        music.setPosition(CatalogUtils.POSITION);

        return music;
    }

    /**
     * Returns music.
     *
     * @return music
     */
    public static Music getMusic() {
        final Music music = new Music();
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
     * @param actual   actual music
     */
    public static void assertMusicDeepEquals(final MusicFO expected, final Music actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getWikiEn(), is(expected.getWikiEn()));
        assertThat(actual.getWikiCz(), is(expected.getWikiCz()));
        assertThat(actual.getMediaCount(), is(Integer.valueOf(expected.getMediaCount())));
        assertThat(actual.getNote(), is(expected.getNote()));
        assertThat(actual.getPosition(), is(expected.getPosition()));
    }

}
