package cz.vhromada.catalog.web.mapper.impl

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.web.fo.MusicFO
import cz.vhromada.catalog.web.mapper.MusicMapper
import org.springframework.stereotype.Component

/**
 * A class represents implementation of mapper for music.
 *
 * @author Vladimir Hromada
 */
@Component("webMusicMapper")
class MusicMapperImpl : MusicMapper {

    override fun map(source: Music): MusicFO {
        return MusicFO(id = source.id,
                name = source.name,
                wikiEn = source.wikiEn,
                wikiCz = source.wikiCz,
                mediaCount = source.mediaCount!!.toString(),
                note = source.note,
                position = source.position)
    }

    override fun mapBack(source: MusicFO): Music {
        return Music(id = source.id,
                name = source.name,
                wikiEn = source.wikiEn,
                wikiCz = source.wikiCz,
                mediaCount = source.mediaCount!!.toInt(),
                note = source.note,
                position = source.position)
    }

}
