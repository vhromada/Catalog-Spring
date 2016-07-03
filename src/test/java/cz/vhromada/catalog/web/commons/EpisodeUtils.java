package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.facade.to.EpisodeTO;
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
        episode.setId(TestConstants.ID);
        episode.setNumber(Integer.toString(TestConstants.NUMBER));
        episode.setName(TestConstants.NAME);
        episode.setLength(TimeUtils.getTimeFO());
        episode.setNote(TestConstants.NOTE);
        episode.setPosition(TestConstants.POSITION);

        return episode;
    }

    /**
     * Returns TO for episode.
     *
     * @return TO for episode
     */
    public static EpisodeTO getEpisodeTO() {
        final EpisodeTO episode = new EpisodeTO();
        episode.setId(TestConstants.ID);
        episode.setNumber(TestConstants.NUMBER);
        episode.setName(TestConstants.NAME);
        episode.setLength(TestConstants.LENGTH);
        episode.setNote(TestConstants.NOTE);
        episode.setPosition(TestConstants.POSITION);

        return episode;
    }

    /**
     * Asserts episode deep equals.
     *
     * @param expected expected FO for episode
     * @param actual   actual TO for episode
     */
    public static void assertEpisodeDeepEquals(final EpisodeFO expected, final EpisodeTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getNumber());
        assertNotNull(actual.getLength());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getNumber(), Integer.toString(actual.getNumber()));
        assertEquals(expected.getName(), actual.getName());
        TimeUtils.assertTimeDeepEquals(expected.getLength(), actual.getLength());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
