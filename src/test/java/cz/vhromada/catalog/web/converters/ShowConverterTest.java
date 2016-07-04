package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.commons.ShowUtils;
import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link ShowFO} to {@link ShowTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class ShowConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with IMDB code.
     */
    @Test
    public void testConvertShowFO_Imdb() {
        final ShowFO showFO = ShowUtils.getShowFO();

        final ShowTO showTO = converter.convert(showFO, ShowTO.class);

        ShowUtils.assertShowDeepEquals(showFO, showTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO without IMDB code.
     */
    @Test
    public void testConvertShowFO_NoImdb() {
        final ShowFO showFO = ShowUtils.getShowFO();
        showFO.setImdb(false);

        final ShowTO showTO = converter.convert(showFO, ShowTO.class);

        ShowUtils.assertShowDeepEquals(showFO, showTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertShowFO_NullArgument() {
        assertNull(converter.convert(null, ShowTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with IMDB code.
     */
    @Test
    public void testConvertShowTO_Imdb() {
        final ShowTO showTO = ShowUtils.getShowTO();

        final ShowFO showFO = converter.convert(showTO, ShowFO.class);

        ShowUtils.assertShowDeepEquals(showTO, showFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO without IMDB code.
     */
    @Test
    public void testConvertShowTO_NoImdb() {
        final ShowTO showTO = ShowUtils.getShowTO();
        showTO.setImdbCode(-1);

        final ShowFO showFO = converter.convert(showTO, ShowFO.class);

        ShowUtils.assertShowDeepEquals(showTO, showFO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertShowTO_NullArgument() {
        assertNull(converter.convert(null, ShowFO.class));
    }

}
