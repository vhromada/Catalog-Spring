package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.web.common.TimeUtils;
import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link TimeFO} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class TimeConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to integer.
     */
    @Test
    void convertTimeFO() {
        final TimeFO time = TimeUtils.getTimeFO();

        final Integer result = converter.convert(time, Integer.class);

        TimeUtils.assertTimeDeepEquals(time, result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to integer with null FO for time.
     */
    @Test
    void convertTimeFO_NullTimeFO() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to FO.
     */
    @Test
    void convertInteger() {
        final Integer length = 100;

        final TimeFO time = converter.convert(length, TimeFO.class);

        TimeUtils.assertTimeDeepEquals(time, length);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to FO with null integer.
     */
    @Test
    void convertInteger_NullInteger() {
        assertThat(converter.convert(null, TimeFO.class)).isNull();
    }

}
