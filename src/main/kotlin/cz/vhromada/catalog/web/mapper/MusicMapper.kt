package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.web.fo.MusicFO

/**
 * An interface represents mapper for music.
 *
 * @author Vladimir Hromada
 */
interface MusicMapper {

    /**
     * Returns FO for music.
     *
     * @param source music
     * @return FO for music
     */
    fun map(source: Music): MusicFO

    /**
     * Returns music.
     *
     * @param source FO for music
     * @return music
     */
    fun mapBack(source: MusicFO): Music

}
