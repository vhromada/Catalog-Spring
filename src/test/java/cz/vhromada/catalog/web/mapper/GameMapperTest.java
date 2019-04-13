package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.web.common.GameUtils;
import cz.vhromada.catalog.web.fo.GameFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Game} and {@link GameFO}.
 *
 * @author Vladimir Hromada
 */
class GameMapperTest {

    /**
     * Mapper for games
     */
    private GameMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(GameMapper.class);
    }

    /**
     * Test method for {@link GameMapper#map(Game)}.
     */
    @Test
    void map() {
        final Game game = GameUtils.getGame();

        final GameFO gameFO = mapper.map(game);

        GameUtils.assertGameDeepEquals(gameFO, game);
    }

    /**
     * Test method for {@link GameMapper#map(Game)} with null game.
     */
    @Test
    void map_NullGame() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link GameMapper#mapBack(GameFO)}.
     */
    @Test
    void mapBack() {
        final GameFO gameFO = GameUtils.getGameFO();

        final Game game = mapper.mapBack(gameFO);

        GameUtils.assertGameDeepEquals(gameFO, game);
    }

    /**
     * Test method for {@link GameMapper#mapBack(GameFO)} with null game.
     */
    @Test
    void mapBack_NullGame() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
