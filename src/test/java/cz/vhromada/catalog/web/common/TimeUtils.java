package cz.vhromada.catalog.web.common;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.common.Time;

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
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertTimeDeepEquals(expected, new Time(actual));
    }

    /**
     * Asserts time deep equals.
     *
     * @param expected expected time
     * @param actual   actual length
     */
    public static void assertTimeDeepEquals(final TimeFO expected, final Time actual) {
        assertSoftly(softly -> {
            softly.assertThat(expected).isNotNull();
            softly.assertThat(actual).isNotNull();
        });
        assertSoftly(softly -> {
            softly.assertThat(actual.getData(Time.TimeData.HOUR)).isEqualTo(Integer.parseInt(expected.getHours()));
            softly.assertThat(actual.getData(Time.TimeData.MINUTE)).isEqualTo(Integer.parseInt(expected.getMinutes()));
            softly.assertThat(actual.getData(Time.TimeData.SECOND)).isEqualTo(Integer.parseInt(expected.getSeconds()));
        });
    }

}
