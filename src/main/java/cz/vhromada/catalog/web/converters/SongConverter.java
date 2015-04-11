package cz.vhromada.catalog.web.converters;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.SongTO;
import cz.vhromada.catalog.web.fo.SongFO;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * A class represents converter between FO for song and TO for song.
 *
 * @author Vladimir Hromada
 */
public class SongConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (sourceFieldValue instanceof SongFO && sourceClass == SongFO.class && destinationClass == SongTO.class) {
            return convertSongFO((SongFO) sourceFieldValue);
        } else if (sourceFieldValue instanceof SongTO && sourceClass == SongTO.class && destinationClass == SongFO.class) {
            return convertSongTO((SongTO) sourceFieldValue);
        } else {
            throw new MappingException("Converter SongConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue
                    + " and " + sourceFieldValue);
        }
    }

    /**
     * Returns converted FO for song to TO for song.
     *
     * @param source FO for song
     * @return converted FO for song to TO for song
     */
    private static SongTO convertSongFO(final SongFO source) {
        final SongTO song = new SongTO();
        song.setId(source.getId());
        song.setName(source.getName());
        song.setLength(convertLength(source.getHours(), source.getMinutes(), source.getSeconds()).getLength());
        song.setNote(source.getNote());
        song.setPosition(source.getPosition());

        return song;
    }

    /**
     * Returns converted TO for song to FO for song.
     *
     * @param source TO for song
     * @return converted TO for song to FO for song
     */
    private static SongFO convertSongTO(final SongTO source) {
        final SongFO song = new SongFO();
        song.setId(source.getId());
        song.setName(source.getName());
        if (source.getLength() > 0) {
            final Time length = new Time(source.getLength());
            song.setHours(String.valueOf(length.getData(Time.TimeData.HOUR)));
            song.setMinutes(String.valueOf(length.getData(Time.TimeData.MINUTE)));
            song.setSeconds(String.valueOf(length.getData(Time.TimeData.SECOND)));
        }
        song.setNote(source.getNote());
        song.setPosition(source.getPosition());

        return song;
    }

    /**
     * Returns converted length.
     *
     * @param hours   hours
     * @param minutes minutes
     * @param seconds seconds
     * @return converted length
     */
    private static Time convertLength(final String hours, final String minutes, final String seconds) {
        if (hours == null || minutes == null || seconds == null) {
            return new Time(0);
        }

        return new Time(convertString(hours), convertString(minutes), convertString(seconds));
    }

    /**
     * Returns converted string to integer.
     *
     * @param source string
     * @return converted string to integer
     */
    private static Integer convertString(final String source) {
        return Integer.valueOf(source);
    }

}
