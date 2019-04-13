package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.web.fo.MusicFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for music.
 *
 * @author Vladimir Hromada
 */
@Mapper
public interface MusicMapper {

    /**
     * Returns FO for music.
     *
     * @param source music
     * @return FO for music
     */
    MusicFO map(Music source);

    /**
     * Returns music.
     *
     * @param source FO for music
     * @return music
     */
    Music mapBack(MusicFO source);

}
