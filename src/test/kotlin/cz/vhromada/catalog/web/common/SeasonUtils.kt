package cz.vhromada.catalog.web.common

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.web.fo.SeasonFO
import cz.vhromada.common.Language
import org.assertj.core.api.SoftAssertions.assertSoftly

/**
 * A class represents utility class for seasons.
 *
 * @author Vladimir Hromada
 */
object SeasonUtils {

    /**
     * Returns FO for season.
     *
     * @return FO for season
     */
    fun getSeasonFO(): SeasonFO {
        return SeasonFO(id = CatalogUtils.ID,
                number = CatalogUtils.NUMBER.toString(),
                startYear = CatalogUtils.YEAR.toString(),
                endYear = (CatalogUtils.YEAR + 1).toString(),
                language = Language.EN,
                subtitles = listOf(Language.CZ),
                note = CatalogUtils.NOTE,
                position = CatalogUtils.POSITION)
    }

    /**
     * Returns season.
     *
     * @return season
     */
    fun getSeason(): Season {
        return Season(id = CatalogUtils.ID,
                number = CatalogUtils.NUMBER,
                startYear = CatalogUtils.YEAR,
                endYear = CatalogUtils.YEAR + 1,
                language = Language.EN,
                subtitles = listOf(Language.CZ),
                note = CatalogUtils.NOTE,
                position = CatalogUtils.POSITION)
    }

    /**
     * Asserts season deep equals.
     *
     * @param expected expected FO for season
     * @param actual   actual season
     */
    fun assertSeasonDeepEquals(expected: SeasonFO?, actual: Season?) {
        assertSoftly {
            it.assertThat(expected).isNotNull
            it.assertThat(actual).isNotNull
        }
        assertSoftly {
            it.assertThat(actual!!.id).isEqualTo(expected!!.id)
            it.assertThat(actual.number).isEqualTo(Integer.parseInt(expected.number))
            it.assertThat(actual.startYear).isEqualTo(Integer.parseInt(expected.startYear))
            it.assertThat(actual.endYear).isEqualTo(Integer.parseInt(expected.endYear))
            it.assertThat<Language>(actual.language).isEqualTo(expected.language)
            it.assertThat<Language>(actual.subtitles).isEqualTo(expected.subtitles)
            it.assertThat(actual.note).isEqualTo(expected.note)
            it.assertThat(actual.position).isEqualTo(expected.position)
        }
    }

}
