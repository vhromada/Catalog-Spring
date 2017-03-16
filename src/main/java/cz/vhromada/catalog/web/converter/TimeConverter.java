package cz.vhromada.catalog.web.converter;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.web.fo.TimeFO;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

/**
 * A class represents converter between {@link TimeFO} and {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@Component("timeConverter")
public class TimeConverter extends BidirectionalConverter<TimeFO, Integer> {

    @Override
    public Integer convertTo(final TimeFO source, final Type<Integer> type, final MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        final int hours = Integer.valueOf(source.getHours());
        final int minutes = Integer.valueOf(source.getMinutes());
        final int seconds = Integer.valueOf(source.getSeconds());
        return new Time(hours, minutes, seconds).getLength();
    }

    @Override
    public TimeFO convertFrom(final Integer source, final Type<TimeFO> type, final MappingContext mappingContext) {
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

}
