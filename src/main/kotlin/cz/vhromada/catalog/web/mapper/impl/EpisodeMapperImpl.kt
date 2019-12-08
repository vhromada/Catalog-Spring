package cz.vhromada.catalog.web.mapper.impl

import cz.vhromada.catalog.entity.Episode
import cz.vhromada.catalog.web.fo.EpisodeFO
import cz.vhromada.catalog.web.mapper.EpisodeMapper
import cz.vhromada.catalog.web.mapper.TimeMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for episodes.
 *
 * @author Vladimir Hromada
 */
@Component("webEpisodeMapper")
class EpisodeMapperImpl(private val timeMapper: TimeMapper) : EpisodeMapper {

    override fun map(source: Episode): EpisodeFO {
        return EpisodeFO(id = source.id,
                number = source.number!!.toString(),
                length = timeMapper.map(source.length!!),
                name = source.name,
                note = source.note,
                position = source.position)
    }

    override fun mapBack(source: EpisodeFO): Episode {
        return Episode(id = source.id,
                number = source.number!!.toInt(),
                length = timeMapper.mapBack(source.length!!),
                name = source.name,
                note = source.note,
                position = source.position)
    }

}
