package cz.vhromada.catalog.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.web.common.TimeUtils;
import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link TimeFO} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class TimeConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to integer.
     */
    @Test
    public void convertTimeFO() {
        final TimeFO time = TimeUtils.getTimeFO();

        final Integer result = converter.convert(time, Integer.class);

        TimeUtils.assertTimeDeepEquals(time, result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to integer with null FO for time.
     */
    @Test
    public void convertTimeFO_NullTimeFO() {
        assertThat(converter.convert(null, Integer.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to FO.
     */
    @Test
    public void convertInteger() {
        final Integer length = 100;

        final TimeFO time = converter.convert(length, TimeFO.class);

        TimeUtils.assertTimeDeepEquals(time, length);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to FO with null integer.
     */
    @Test
    public void convertInteger_NullInteger() {
        assertThat(converter.convert(null, TimeFO.class), is(nullValue()));
    }

}
