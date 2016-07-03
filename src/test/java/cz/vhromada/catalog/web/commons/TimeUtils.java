package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import cz.vhromada.catalog.commons.Time;
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
     * @param expected expected TO for time
     * @param actual   actual length
     */
    public static void assertTimeDeepEquals(final TimeFO expected, final Integer actual) {
        assertNotNull(actual);
        assertTimeDeepEquals(expected, new Time(actual));
    }

    /**
     * Asserts time deep equals.
     *
     * @param expected expected TO for time
     * @param actual   actual length
     */
    public static void assertTimeDeepEquals(final TimeFO expected, final Time actual) {
        assertNotNull(actual);
        assertEquals(expected.getHours(), Integer.toString(actual.getData(Time.TimeData.HOUR)));
        assertEquals(expected.getMinutes(), Integer.toString(actual.getData(Time.TimeData.MINUTE)));
        assertEquals(expected.getSeconds(), Integer.toString(actual.getData(Time.TimeData.SECOND)));
    }

}
