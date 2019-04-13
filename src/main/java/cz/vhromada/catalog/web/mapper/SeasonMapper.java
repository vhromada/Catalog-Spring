package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.web.fo.SeasonFO;

import org.mapstruct.Mapper;

/**
 * An interface represents mapper for seasons.
 *
 * @author Vladimir Hromada
 */
@Mapper
public interface SeasonMapper {

    /**
     * Returns FO for season.
     *
     * @param source season
     * @return FO for season
     */
    SeasonFO map(Season source);

    /**
     * Returns season.
     *
     * @param source FO for season
     * @return season
     */
    Season mapBack(SeasonFO source);

}
