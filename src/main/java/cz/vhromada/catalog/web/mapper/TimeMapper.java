package cz.vhromada.catalog.web.mapper;

import cz.vhromada.catalog.web.fo.TimeFO;
import cz.vhromada.common.Time;

import org.mapstruct.Mapper;

/**
 * An abstract class represents mapper for time.
 *
 * @author Vladimir Hromada
 */
@Mapper
public abstract class TimeMapper {

    /**
     * Returns FO for time.
     *
     * @param source length
     * @return time
     */
    public TimeFO map(final Integer source) {
        if (source == null) {
            return null;
        }

        final Time time = new Time(source);

        final TimeFO result = new TimeFO();
        result.setHours(Integer.toString(time.getData(Time.TimeData.HOUR)));
        result.setMinutes(Integer.toString(time.getData(Time.TimeData.MINUTE)));
        result.setSeconds(Integer.toString(time.getData(Time.TimeData.SECOND)));
        return result;
    }

    /**
     * Returns length.
     *
     * @param source FO for time
     * @return length
     */
    public Integer mapBack(final TimeFO source) {
        if (source == null) {
            return null;
        }

        final int hours = Integer.parseInt(source.getHours());
        final int minutes = Integer.parseInt(source.getMinutes());
        final int seconds = Integer.parseInt(source.getSeconds());
        return new Time(hours, minutes, seconds).getLength();
    }

}
