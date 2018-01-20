package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.web.common.GameUtils;
import cz.vhromada.catalog.web.fo.GameFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link GameFO} to {@link Game}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class GameConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertGameFO() {
        final GameFO gameFO = GameUtils.getGameFO();

        final Game game = converter.convert(gameFO, Game.class);

        GameUtils.assertGameDeepEquals(gameFO, game);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for game.
     */
    @Test
    void convertGameFO_NullGameFO() {
        assertThat(converter.convert(null, Game.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertGame() {
        final Game game = GameUtils.getGame();

        final GameFO gameFO = converter.convert(game, GameFO.class);

        GameUtils.assertGameDeepEquals(gameFO, game);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null game.
     */
    @Test
    void convertGame_NullGame() {
        assertThat(converter.convert(null, GameFO.class)).isNull();
    }

}
