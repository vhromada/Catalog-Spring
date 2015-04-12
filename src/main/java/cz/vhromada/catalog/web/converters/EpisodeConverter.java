package cz.vhromada.catalog.web.converters;

import cz.vhromada.catalog.commons.Time;
import cz.vhromada.catalog.facade.to.EpisodeTO;
import cz.vhromada.catalog.web.fo.EpisodeFO;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

/**
 * A class represents converter between FO for episode and TO for episode.
 *
 * @author Vladimir Hromada
 */
public class EpisodeConverter implements CustomConverter {

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (sourceFieldValue instanceof EpisodeFO && sourceClass == EpisodeFO.class && destinationClass == EpisodeTO.class) {
            return convertEpisodeFO((EpisodeFO) sourceFieldValue);
        } else if (sourceFieldValue instanceof EpisodeTO && sourceClass == EpisodeTO.class && destinationClass == EpisodeFO.class) {
            return convertEpisodeTO((EpisodeTO) sourceFieldValue);
        } else {
            throw new MappingException("Converter EpisodeConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue + " and "
                    + sourceFieldValue);
        }
    }

    /**
     * Returns converted FO for episode to TO for episode.
     *
     * @param source FO for episode
     * @return converted FO for episode to TO for episode
     */
    private static EpisodeTO convertEpisodeFO(final EpisodeFO source) {
        final EpisodeTO episode = new EpisodeTO();
        episode.setId(source.getId());
        episode.setNumber(convertString(source.getNumber()));
        episode.setName(source.getName());
        episode.setLength(convertLength(source.getHours(), source.getMinutes(), source.getSeconds()).getLength());
        episode.setNote(source.getNote());
        episode.setPosition(source.getPosition());

        return episode;
    }

    /**
     * Returns converted TO for episode to FO for episode.
     *
     * @param source TO for episode
     * @return converted TO for episode to FO for episode
     */
    private static EpisodeFO convertEpisodeTO(final EpisodeTO source) {
        final EpisodeFO episode = new EpisodeFO();
        episode.setId(source.getId());
        episode.setNumber(String.valueOf(source.getNumber()));
        episode.setName(source.getName());
        if (source.getLength() > 0) {
            final Time length = new Time(source.getLength());
            episode.setHours(String.valueOf(length.getData(Time.TimeData.HOUR)));
            episode.setMinutes(String.valueOf(length.getData(Time.TimeData.MINUTE)));
            episode.setSeconds(String.valueOf(length.getData(Time.TimeData.SECOND)));
        }
        episode.setNote(source.getNote());
        episode.setPosition(source.getPosition());

        return episode;
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
