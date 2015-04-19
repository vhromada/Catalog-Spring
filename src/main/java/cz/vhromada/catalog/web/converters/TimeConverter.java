package cz.vhromada.catalog.web.converters;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.web.fo.TimeFO;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * A class represents converter between FO for time and length.
 *
 * @author Vladimir Hromada
 */
public class TimeConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }

        if (sourceFieldValue instanceof TimeFO && sourceClass == TimeFO.class && (destinationClass == Integer.class || destinationClass == int.class)) {
            return convertTimeFO((TimeFO) sourceFieldValue);
        } else if (sourceFieldValue instanceof Integer && sourceClass == Integer.class && destinationClass == TimeFO.class) {
            return convertLength((Integer) sourceFieldValue);
        } else {
            throw new MappingException("Converter TimeConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue + " and "
                    + sourceFieldValue);
        }
    }

    /**
     * Returns converted FO for time to length.
     *
     * @param source FO for time
     * @return converted FO for time to length
     */
    private static Integer convertTimeFO(final TimeFO source) {
        final int hours = Integer.parseInt(source.getHours());
        final int minutes = Integer.parseInt(source.getMinutes());
        final int seconds = Integer.parseInt(source.getSeconds());

        return new Time(hours, minutes, seconds).getLength();
    }

    /**
     * Returns converted length to FO for time.
     *
     * @param source length
     * @return converted length to FO for time
     */
    private static TimeFO convertLength(final Integer source) {
        final Time length = new Time(source);

        final TimeFO time = new TimeFO();
        time.setHours(Integer.toString(length.getData(Time.TimeData.HOUR)));
        time.setMinutes(Integer.toString(length.getData(Time.TimeData.MINUTE)));
        time.setSeconds(Integer.toString(length.getData(Time.TimeData.SECOND)));

        return time;
    }

}
