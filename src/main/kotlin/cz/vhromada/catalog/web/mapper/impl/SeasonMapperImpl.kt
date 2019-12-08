package cz.vhromada.catalog.web.mapper.impl

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.web.fo.SeasonFO
import cz.vhromada.catalog.web.mapper.SeasonMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for seasons.
 *
 * @author Vladimir Hromada
 */
@Component("webSeasonMapper")
class SeasonMapperImpl : SeasonMapper {

    override fun map(source: Season): SeasonFO {
        return SeasonFO(id = source.id,
                number = source.number.toString(),
                startYear = source.startYear.toString(),
                endYear = source.endYear.toString(),
                language = source.language,
                subtitles = source.subtitles!!.filterNotNull(),
                note = source.note,
                position = source.position)
    }

    override fun mapBack(source: SeasonFO): Season {
        return Season(id = source.id,
                number = source.number!!.toInt(),
                startYear = source.startYear!!.toInt(),
                endYear = source.endYear!!.toInt(),
                language = source.language,
                subtitles = source.subtitles,
                note = source.note,
                position = source.position)
    }

}
