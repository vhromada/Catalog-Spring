package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.common.ShowUtils;
import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link ShowFO} to {@link Show}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class ShowConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with IMDB code.
     */
    @Test
    void convertShowFO_Imdb() {
        final ShowFO showFO = ShowUtils.getShowFO();

        final Show show = converter.convert(showFO, Show.class);

        ShowUtils.assertShowDeepEquals(showFO, show);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity without IMDB code.
     */
    @Test
    void convertShowFO_NoImdb() {
        final ShowFO showFO = ShowUtils.getShowFO();
        showFO.setImdb(false);
        showFO.setImdbCode("");

        final Show show = converter.convert(showFO, Show.class);

        ShowUtils.assertShowDeepEquals(showFO, show);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for show.
     */
    @Test
    void convertShowFO_NullShowFO() {
        assertThat(converter.convert(null, Show.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with IMDB code.
     */
    @Test
    void convertShow_Imdb() {
        final Show show = ShowUtils.getShow();

        final ShowFO showFO = converter.convert(show, ShowFO.class);

        ShowUtils.assertShowDeepEquals(show, showFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO without IMDB code.
     */
    @Test
    void convertShow_NoImdb() {
        final Show show = ShowUtils.getShow();
        show.setImdbCode(-1);

        final ShowFO showFO = converter.convert(show, ShowFO.class);

        ShowUtils.assertShowDeepEquals(show, showFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with entity.
     */
    @Test
    void convertShow_NullEntity() {
        assertThat(converter.convert(null, ShowFO.class)).isNull();
    }

}
