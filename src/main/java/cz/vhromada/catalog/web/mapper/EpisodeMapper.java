package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.web.fo.EpisodeFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for episodes.
 *
 * @author Vladimir Hromada
 */
@Mapper(uses = TimeMapper.class)
public interface EpisodeMapper {

    /**
     * Returns FO for episode.
     *
     * @param source episode
     * @return FO for episode
     */
    EpisodeFO map(Episode source);

    /**
     * Returns episode.
     *
     * @param source FO for episode
     * @return episode
     */
    Episode mapBack(EpisodeFO source);

}
