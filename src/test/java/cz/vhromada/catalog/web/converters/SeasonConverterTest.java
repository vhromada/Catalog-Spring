package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.web.commons.SeasonUtils;
import cz.vhromada.catalog.web.fo.SeasonFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link SeasonFO} to {@link SeasonTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class SeasonConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO.
     */
    @Test
    public void testConvertSeasonFO() {
        final SeasonFO seasonFO = SeasonUtils.getSeasonFO();

        final SeasonTO seasonTO = converter.convert(seasonFO, SeasonTO.class);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, seasonTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertSeasonFO_NullArgument() {
        assertNull(converter.convert(null, SeasonTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertSeasonTO() {
        final SeasonTO seasonTO = SeasonUtils.getSeasonTO();

        final SeasonFO seasonFO = converter.convert(seasonTO, SeasonFO.class);

        SeasonUtils.assertSeasonDeepEquals(seasonFO, seasonTO);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertSeasonTO_NullArgument() {
        assertNull(converter.convert(null, SeasonFO.class));
    }

}
