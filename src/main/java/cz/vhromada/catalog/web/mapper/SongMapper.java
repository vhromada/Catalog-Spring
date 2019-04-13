package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.web.fo.SongFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for songs.
 *
 * @author Vladimir Hromada
 */
@Mapper(uses = TimeMapper.class)
public interface SongMapper {

    /**
     * Returns FO for song.
     *
     * @param source song
     * @return FO for song
     */
    SongFO map(Song source);

    /**
     * Returns song.
     *
     * @param source FO for song
     * @return song
     */
    Song mapBack(SongFO source);

}
