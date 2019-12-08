package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.web.fo.SeasonFO

/**
 * An interface represents mapper for seasons.
 *
 * @author Vladimir Hromada
 */
interface SeasonMapper {

    /**
     * Returns FO for season.
     *
     * @param source season
     * @return FO for season
     */
    fun map(source: Season): SeasonFO

    /**
     * Returns season.
     *
     * @param source FO for season
     * @return season
     */
    fun mapBack(source: SeasonFO): Season

}
