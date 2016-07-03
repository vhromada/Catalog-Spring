package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.facade.to.GameTO;
import cz.vhromada.catalog.web.fo.GameFO;

/**
 * A class represents utility class for games.
 *
 * @author Vladimir Hromada
 */
public final class GameUtils {

    /**
     * Creates a new instance of GameUtils.
     */
    private GameUtils() {
    }

    /**
     * Returns FO for game.
     *
     * @return FO for game
     */
    public static GameFO getGameFO() {
        final GameFO game = new GameFO();
        game.setId(1);
        game.setName("Name");
        game.setWikiEn("enWiki");
        game.setWikiCz("czWiki");
        game.setMediaCount("1");
        game.setCrack(true);
        game.setSerialKey(true);
        game.setPatch(true);
        game.setTrainer(true);
        game.setTrainerData(true);
        game.setEditor(true);
        game.setSaves(true);
        game.setOtherData("Other data");
        game.setNote("Note");
        game.setPosition(0);

        return game;
    }

    /**
     * Returns TO for game.
     *
     * @return TO for game
     */
    public static GameTO getGameTO() {
        final GameTO game = new GameTO();
        game.setId(1);
        game.setName("Name");
        game.setWikiEn("enWiki");
        game.setWikiCz("czWiki");
        game.setMediaCount(1);
        game.setCrack(true);
        game.setSerialKey(true);
        game.setPatch(true);
        game.setTrainer(true);
        game.setTrainerData(true);
        game.setEditor(true);
        game.setSaves(true);
        game.setOtherData("Other data");
        game.setNote("Note");
        game.setPosition(0);

        return game;
    }

    /**
     * Asserts game deep equals.
     *
     * @param expected expected FO for game
     * @param actual   actual TO for game
     */
    public static void assertGameDeepEquals(final GameFO expected, final GameTO actual) {
        assertNotNull(actual);
        assertNotNull(actual.getMediaCount());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(expected.getMediaCount(), Integer.toString(actual.getMediaCount()));
        assertEquals(expected.getCrack(), actual.getCrack());
        assertEquals(expected.getSerialKey(), actual.getSerialKey());
        assertEquals(expected.getPatch(), actual.getPatch());
        assertEquals(expected.getTrainer(), actual.getTrainer());
        assertEquals(expected.getTrainerData(), actual.getTrainerData());
        assertEquals(expected.getEditor(), actual.getEditor());
        assertEquals(expected.getSaves(), actual.getSaves());
        assertEquals(expected.getOtherData(), actual.getOtherData());
        assertEquals(expected.getNote(), actual.getNote());
        assertEquals(expected.getPosition(), actual.getPosition());
    }

}
