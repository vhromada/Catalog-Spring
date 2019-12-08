package cz.vhromada.catalog.web.domain

import cz.vhromada.catalog.entity.Season
import cz.vhromada.common.Time
import java.io.Serializable
import java.util.Objects

/**
 * A class represents season data.
 *
 * @author Vladimir Hromada
 */
data class SeasonData(

        /**
         * Season
         */
        val season: Season,

        /**
         * Count of episodes
         */
        val episodesCount: Int,

        /**
         * Total length
         */
        val totalLength: Time) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is SeasonData) {
            false
        } else season == other.season
    }

    override fun hashCode(): Int {
        return Objects.hashCode(season)
    }

}
