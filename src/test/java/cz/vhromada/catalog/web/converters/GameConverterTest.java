package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.GameTO;
import cz.vhromada.catalog.web.fo.GameFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link GameFO} to {@link GameTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class GameConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO.
     */
    @Test
    public void testConvertGameFO() {
        final GameFO gameFO = getGameFO();

        final GameTO gameTO = converter.convert(gameFO, GameTO.class);

        assertGameDeepEquals(gameTO, gameFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertGameFO_NullArgument() {
        assertNull(converter.convert(null, GameTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertGameTO() {
        final GameTO gameTO = getGameTO();

        final GameFO gameFO = converter.convert(gameTO, GameFO.class);

        assertGameDeepEquals(gameTO, gameFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertGameTO_NullArgument() {
        assertNull(converter.convert(null, GameFO.class));
    }

    /**
     * Returns FO for game.
     *
     * @return FO for game
     */
    private static GameFO getGameFO() {
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

        return game;
    }

    /**
     * Returns TO for game.
     *
     * @return TO for game
     */
    private static GameTO getGameTO() {
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

        return game;
    }

    /**
     * Asserts game deep equals.
     *
     * @param expected expected TO for game
     * @param actual   actual game
     */
    public static void assertGameDeepEquals(final GameTO expected, final GameFO actual) {
        assertNotNull(expected);
        assertNotNull(actual);
        assertNotNull(expected.getMediaCount());
        assertNotNull(actual.getMediaCount());
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getWikiEn(), actual.getWikiEn());
        assertEquals(expected.getWikiCz(), actual.getWikiCz());
        assertEquals(Integer.toString(expected.getMediaCount()), actual.getMediaCount());
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
