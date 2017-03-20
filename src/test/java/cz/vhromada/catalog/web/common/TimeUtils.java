package cz.vhromada.catalog.web.common;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.web.fo.TimeFO;

/**
 * A class represents utility class for time.
 *
 * @author Vladimir Hromada
 */
public final class TimeUtils {

    /**
     * Creates a new instance of TimeUtils.
     */
    private TimeUtils() {
    }

    /**
     * Returns FO for time.
     *
     * @return FO for time
     */
    public static TimeFO getTimeFO() {
        final TimeFO time = new TimeFO();
        time.setHours("1");
        time.setMinutes("2");
        time.setSeconds("3");

        return time;
    }

    /**
     * Asserts time deep equals.
     *
     * @param expected expected time
     * @param actual   actual length
     */
    public static void assertTimeDeepEquals(final TimeFO expected, final Integer actual) {
        assertThat(actual, is(notNullValue()));
        assertTimeDeepEquals(expected, new Time(actual));
    }

    /**
     * Asserts time deep equals.
     *
     * @param expected expected time
     * @param actual   actual length
     */
    public static void assertTimeDeepEquals(final TimeFO expected, final Time actual) {
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getData(Time.TimeData.HOUR), is(Integer.parseInt(expected.getHours())));
        assertThat(actual.getData(Time.TimeData.MINUTE), is(Integer.parseInt(expected.getMinutes())));
        assertThat(actual.getData(Time.TimeData.SECOND), is(Integer.parseInt(expected.getSeconds())));
    }

}
