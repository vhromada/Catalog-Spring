package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Game
import cz.vhromada.catalog.web.fo.GameFO

/**
 * An interface represents mapper for games.
 *
 * @author Vladimir Hromada
 */
interface GameMapper {

    /**
     * Returns FO for game.
     *
     * @param source game
     * @return FO for game
     */
    fun map(source: Game): GameFO

    /**
     * Returns game.
     *
     * @param source FO for game
     * @return game
     */
    fun mapBack(source: GameFO): Game

}
