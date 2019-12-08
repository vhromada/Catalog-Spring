package cz.vhromada.catalog.web.domain

import cz.vhromada.catalog.entity.Music
import cz.vhromada.common.Time
import java.io.Serializable
import java.util.Objects

/**
 * A class represents music data.
 *
 * @author Vladimir Hromada
 */
data class MusicData(

        /**
         * Music
         */
        val music: Music,

        /**
         * Count of songs
         */
        val songsCount: Int,

        /**
         * Total length
         */
        val totalLength: Time) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        return if (other !is MusicData) {
            false
        } else music == other.music
    }

    override fun hashCode(): Int {
        return Objects.hashCode(music)
    }

}
