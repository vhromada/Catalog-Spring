package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.web.commons.TimeUtils;
import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link TimeFO} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class TimeConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to integer.
     */
    @Test
    public void testConvertTimeFO() {
        final TimeFO time = TimeUtils.getTimeFO();

        final Integer result = converter.convert(time, Integer.class);

        TimeUtils.assertTimeDeepEquals(time, result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to integer with null argument.
     */
    @Test
    public void testConvertTimeFO_NullArgument() {
        assertNull(converter.convert(null, Integer.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to FO.
     */
    @Test
    public void testConvertInteger() {
        final Integer length = 100;

        final TimeFO time = converter.convert(length, TimeFO.class);

        TimeUtils.assertTimeDeepEquals(time, length);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to FO with null argument.
     */
    @Test
    public void testConvertInteger_NullArgument() {
        assertNull(converter.convert(null, Integer.class));
    }

}
