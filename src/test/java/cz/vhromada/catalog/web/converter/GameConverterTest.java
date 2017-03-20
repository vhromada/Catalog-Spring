package cz.vhromada.catalog.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.web.common.GameUtils;
import cz.vhromada.catalog.web.fo.GameFO;
import cz.vhromada.converter.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link GameFO} to {@link Game}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class GameConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertGameFO() {
        final GameFO gameFO = GameUtils.getGameFO();

        final Game game = converter.convert(gameFO, Game.class);

        GameUtils.assertGameDeepEquals(gameFO, game);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for game.
     */
    @Test
    public void convertGameFO_NullGameFO() {
        assertThat(converter.convert(null, Game.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertGame() {
        final Game game = GameUtils.getGame();

        final GameFO gameFO = converter.convert(game, GameFO.class);

        GameUtils.assertGameDeepEquals(gameFO, game);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null game.
     */
    @Test
    public void convertGame_NullGame() {
        assertThat(converter.convert(null, GameFO.class), is(nullValue()));
    }

}
