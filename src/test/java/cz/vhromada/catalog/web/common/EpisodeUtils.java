package cz.vhromada.catalog.web.common;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.web.fo.EpisodeFO;

/**
 * A class represents utility class for episodes.
 *
 * @author Vladimir Hromada
 */
public final class EpisodeUtils {

    /**
     * Creates a new instance of EpisodeUtils.
     */
    private EpisodeUtils() {
    }

    /**
     * Returns FO for episode.
     *
     * @return FO for episode
     */
    public static EpisodeFO getEpisodeFO() {
        final EpisodeFO episode = new EpisodeFO();
        episode.setId(CatalogUtils.ID);
        episode.setNumber(Integer.toString(CatalogUtils.NUMBER));
        episode.setName(CatalogUtils.NAME);
        episode.setLength(TimeUtils.getTimeFO());
        episode.setNote(CatalogUtils.NOTE);
        episode.setPosition(CatalogUtils.POSITION);

        return episode;
    }

    /**
     * Returns episode.
     *
     * @return episode
     */
    public static Episode getEpisode() {
        final Episode episode = new Episode();
        episode.setId(CatalogUtils.ID);
        episode.setNumber(CatalogUtils.NUMBER);
        episode.setName(CatalogUtils.NAME);
        episode.setLength(CatalogUtils.LENGTH);
        episode.setNote(CatalogUtils.NOTE);
        episode.setPosition(CatalogUtils.POSITION);

        return episode;
    }

    /**
     * Asserts episode deep equals.
     *
     * @param expected expected FO for episode
     * @param actual   actual episode
     */
    public static void assertEpisodeDeepEquals(final EpisodeFO expected, final Episode actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getNumber()).isEqualTo(Integer.parseInt(expected.getNumber()));
            softly.assertThat(actual.getName()).isEqualTo(expected.getName());
            TimeUtils.assertTimeDeepEquals(expected.getLength(), actual.getLength());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
        });
    }

}
