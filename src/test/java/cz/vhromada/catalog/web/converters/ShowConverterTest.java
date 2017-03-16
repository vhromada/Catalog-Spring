package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.common.ShowUtils;
import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link ShowFO} to {@link Show}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class ShowConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with IMDB code.
     */
    @Test
    public void convertShowFO_Imdb() {
        final ShowFO showFO = ShowUtils.getShowFO();

        final Show show = converter.convert(showFO, Show.class);

        ShowUtils.assertShowDeepEquals(showFO, show);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity without IMDB code.
     */
    @Test
    public void convertShowFO_NoImdb() {
        final ShowFO showFO = ShowUtils.getShowFO();
        showFO.setImdb(false);

        final Show show = converter.convert(showFO, Show.class);

        ShowUtils.assertShowDeepEquals(showFO, show);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for show.
     */
    @Test
    public void convertShowFO_NullShowFO() {
        assertNull(converter.convert(null, Show.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with IMDB code.
     */
    @Test
    public void convertShow_Imdb() {
        final Show show = ShowUtils.getShow();

        final ShowFO showFO = converter.convert(show, ShowFO.class);

        ShowUtils.assertShowDeepEquals(show, showFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO without IMDB code.
     */
    @Test
    public void convertShow_NoImdb() {
        final Show show = ShowUtils.getShow();
        show.setImdbCode(-1);

        final ShowFO showFO = converter.convert(show, ShowFO.class);

        ShowUtils.assertShowDeepEquals(show, showFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with entity.
     */
    @Test
    public void convertShow_NullEntity() {
        assertNull(converter.convert(null, ShowFO.class));
    }

}
