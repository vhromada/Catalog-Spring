package cz.vhromada.catalog.web.mapper

import cz.vhromada.catalog.entity.Song
import cz.vhromada.catalog.web.fo.SongFO

/**
 * An interface represents mapper for songs.
 *
 * @author Vladimir Hromada
 */
interface SongMapper {

    /**
     * Returns FO for song.
     *
     * @param source song
     * @return FO for song
     */
    fun map(source: Song): SongFO

    /**
     * Returns song.
     *
     * @param source FO for song
     * @return song
     */
    fun mapBack(source: SongFO): Song

}
