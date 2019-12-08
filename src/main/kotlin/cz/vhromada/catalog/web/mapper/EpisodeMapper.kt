package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Episode
import cz.vhromada.catalog.web.fo.EpisodeFO

/**
 * An interface represents mapper for episodes.
 *
 * @author Vladimir Hromada
 */
interface EpisodeMapper {

    /**
     * Returns FO for episode.
     *
     * @param source episode
     * @return FO for episode
     */
    fun map(source: Episode): EpisodeFO

    /**
     * Returns episode.
     *
     * @param source FO for episode
     * @return episode
     */
    fun mapBack(source: EpisodeFO): Episode

}
