package cz.vhromada.catalog.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Game;
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
        game.setId(CatalogUtils.ID);
        game.setName(CatalogUtils.NAME);
        game.setWikiEn(CatalogUtils.EN_WIKI);
        game.setWikiCz(CatalogUtils.CZ_WIKI);
        game.setMediaCount(CatalogUtils.MEDIA.toString());
        game.setCrack(true);
        game.setSerialKey(true);
        game.setPatch(true);
        game.setTrainer(true);
        game.setTrainerData(true);
        game.setEditor(true);
        game.setSaves(true);
        game.setOtherData("Other data");
        game.setNote(CatalogUtils.NOTE);
        game.setPosition(CatalogUtils.POSITION);

        return game;
    }

    /**
     * Returns game.
     *
     * @return game
     */
    public static Game getGame() {
        final Game game = new Game();
        game.setId(CatalogUtils.ID);
        game.setName(CatalogUtils.NAME);
        game.setWikiEn(CatalogUtils.EN_WIKI);
        game.setWikiCz(CatalogUtils.CZ_WIKI);
        game.setMediaCount(CatalogUtils.MEDIA);
        game.setCrack(true);
        game.setSerialKey(true);
        game.setPatch(true);
        game.setTrainer(true);
        game.setTrainerData(true);
        game.setEditor(true);
        game.setSaves(true);
        game.setOtherData("Other data");
        game.setNote(CatalogUtils.NOTE);
        game.setPosition(CatalogUtils.POSITION);

        return game;
    }

    /**
     * Asserts game deep equals.
     *
     * @param expected expected FO for game
     * @param actual   actual game
     */
    public static void assertGameDeepEquals(final GameFO expected, final Game actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getId(), is(expected.getId()));
        assertThat(actual.getName(), is(expected.getName()));
        assertThat(actual.getWikiEn(), is(expected.getWikiEn()));
        assertThat(actual.getWikiCz(), is(expected.getWikiCz()));
        assertThat(actual.getMediaCount(), is(Integer.parseInt(expected.getMediaCount())));
        assertThat(actual.getCrack(), is(expected.getCrack()));
        assertThat(actual.getSerialKey(), is(expected.getSerialKey()));
        assertThat(actual.getPatch(), is(expected.getPatch()));
        assertThat(actual.getTrainer(), is(expected.getTrainer()));
        assertThat(actual.getTrainerData(), is(expected.getTrainerData()));
        assertThat(actual.getEditor(), is(expected.getEditor()));
        assertThat(actual.getSaves(), is(expected.getSaves()));
        assertThat(actual.getOtherData(), is(expected.getOtherData()));
        assertThat(actual.getNote(), is(expected.getNote()));
        assertThat(actual.getPosition(), is(expected.getPosition()));
    }

}
