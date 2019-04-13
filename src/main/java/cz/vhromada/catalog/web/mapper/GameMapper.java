package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.web.fo.GameFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for games.
 *
 * @author Vladimir Hromada
 */
@Mapper
public interface GameMapper {

    /**
     * Returns FO for game.
     *
     * @param source game
     * @return FO for game
     */
    GameFO map(Game source);

    /**
     * Returns game.
     *
     * @param source FO for game
     * @return game
     */
    Game mapBack(GameFO source);

}
