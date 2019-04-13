package cz.vhromada.catalog.web.common;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.Collections;

import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.fo.ShowFO;

/**
 * A class represents utility class for shows.
 *
 * @author Vladimir Hromada
 */
public final class ShowUtils {

    /**
     * Creates a new instance of ShowUtils.
     */
    private ShowUtils() {
    }

    /**
     * Returns FO for show.
     *
     * @return FO for show
     */
    public static ShowFO getShowFO() {
        final Medium medium = new Medium();
        medium.setId(CatalogUtils.ID);
        medium.setNumber(CatalogUtils.NUMBER);
        medium.setLength(CatalogUtils.LENGTH);

        final ShowFO show = new ShowFO();
        show.setId(CatalogUtils.ID);
        show.setCzechName("czName");
        show.setOriginalName("origName");
        show.setCsfd("Csfd");
        show.setImdb(true);
        show.setImdbCode("1000");
        show.setWikiEn(CatalogUtils.EN_WIKI);
        show.setWikiCz(CatalogUtils.CZ_WIKI);
        show.setPicture(CatalogUtils.ID);
        show.setNote(CatalogUtils.NOTE);
        show.setPosition(CatalogUtils.POSITION);
        show.setGenres(Collections.singletonList(CatalogUtils.ID));

        return show;
    }

    /**
     * Returns show.
     *
     * @return show
     */
    public static Show getShow() {
        final Medium medium = new Medium();
        medium.setId(CatalogUtils.ID);
        medium.setNumber(CatalogUtils.NUMBER);
        medium.setLength(CatalogUtils.LENGTH);

        final Show show = new Show();
        show.setId(CatalogUtils.ID);
        show.setCzechName("czName");
        show.setOriginalName("origName");
        show.setCsfd("Csfd");
        show.setImdbCode(1000);
        show.setWikiEn(CatalogUtils.EN_WIKI);
        show.setWikiCz(CatalogUtils.CZ_WIKI);
        show.setPicture(CatalogUtils.ID);
        show.setNote(CatalogUtils.NOTE);
        show.setPosition(CatalogUtils.POSITION);
        show.setGenres(Collections.singletonList(GenreUtils.getGenre()));

        return show;
    }

    /**
     * Asserts show deep equals.
     *
     * @param expected expected FO for show
     * @param actual   actual show
     */
    public static void assertShowDeepEquals(final ShowFO expected, final Show actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getCzechName()).isEqualTo(expected.getCzechName());
            softly.assertThat(actual.getOriginalName()).isEqualTo(expected.getOriginalName());
            softly.assertThat(actual.getCsfd()).isEqualTo(expected.getCsfd());
            CatalogUtils.assertImdbCodeDeepEquals(expected.getImdb(), expected.getImdbCode(), actual.getImdbCode());
            softly.assertThat(actual.getWikiEn()).isEqualTo(expected.getWikiEn());
            softly.assertThat(actual.getWikiCz()).isEqualTo(expected.getWikiCz());
            softly.assertThat(actual.getPicture()).isEqualTo(expected.getPicture());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
            GenreUtils.assertGenresDeepEquals(expected.getGenres(), actual.getGenres());
        });
    }

    /**
     * Asserts show deep equals.
     *
     * @param expected expected show
     * @param actual   actual FO for show
     */
    public static void assertShowDeepEquals(final Show expected, final ShowFO actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getId()).isEqualTo(expected.getId());
            softly.assertThat(actual.getCzechName()).isEqualTo(expected.getCzechName());
            softly.assertThat(actual.getOriginalName()).isEqualTo(expected.getOriginalName());
            softly.assertThat(actual.getCsfd()).isEqualTo(expected.getCsfd());
            CatalogUtils.assertImdbDeepEquals(expected.getImdbCode(), actual.getImdb(), actual.getImdbCode());
            softly.assertThat(actual.getWikiEn()).isEqualTo(expected.getWikiEn());
            softly.assertThat(actual.getWikiCz()).isEqualTo(expected.getWikiCz());
            softly.assertThat(actual.getPicture()).isEqualTo(expected.getPicture());
            softly.assertThat(actual.getNote()).isEqualTo(expected.getNote());
            softly.assertThat(actual.getPosition()).isEqualTo(expected.getPosition());
            GenreUtils.assertGenreListDeepEquals(expected.getGenres(), actual.getGenres());
        });
    }

}
