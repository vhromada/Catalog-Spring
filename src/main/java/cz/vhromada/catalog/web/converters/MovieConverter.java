package cz.vhromada.catalog.web.converters;

import java.util.ArrayList;
import java.util.List;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.MovieTO;
import cz.vhromada.catalog.web.fo.MovieFO;
import cz.vhromada.catalog.web.fo.TimeFO;

import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;

/**
 * A class represents converter between FO for movie and TO for movie.
 *
 * @author Vladimir Hromada
 */
public class MovieConverter implements MapperAware, CustomConverter {

    /**
     * Mapper
     */
    private Mapper mapper;

    @Override
    public Object convert(final Object existingDestinationFieldValue, final Object sourceFieldValue, final Class<?> destinationClass,
            final Class<?> sourceClass) {
        if (sourceFieldValue == null) {
            return null;
        }
        if (sourceFieldValue instanceof MovieFO && sourceClass == MovieFO.class && destinationClass == MovieTO.class) {
            return convertMovieFO((MovieFO) sourceFieldValue);
        } else if (sourceFieldValue instanceof MovieTO && sourceClass == MovieTO.class && destinationClass == MovieFO.class) {
            return convertMovieTO((MovieTO) sourceFieldValue);
        } else {
            throw new MappingException("Converter MovieConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue + " and "
                    + sourceFieldValue);
        }
    }

    @Override
    public void setMapper(final Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Returns converted FO for movie to TO for movie.
     *
     * @param source FO for movie
     * @return converted FO for movie to TO for movie
     */
    private MovieTO convertMovieFO(final MovieFO source) {
        final MovieTO movie = new MovieTO();
        movie.setId(source.getId());
        movie.setCzechName(source.getCzechName());
        movie.setOriginalName(source.getOriginalName());
        movie.setYear(Integer.parseInt(source.getYear()));
        movie.setLanguage(source.getLanguage());
        movie.setSubtitles(source.getSubtitles());
        movie.setMedia(convertMedia(source.getMedia()));
        movie.setCsfd(source.getCsfd());
        movie.setImdbCode(source.getImdb() ? Integer.parseInt(source.getImdbCode()) : -1);
        movie.setWikiCz(source.getWikiCz());
        movie.setWikiEn(source.getWikiEn());
        movie.setPicture(source.getPicture());
        movie.setNote(source.getNote());
        movie.setPosition(source.getPosition());
        movie.setGenres(convertIdList(source.getGenres()));

        return movie;
    }

    /**
     * Returns media.
     *
     * @param source source
     * @return media
     */
    private List<Integer> convertMedia(final List<TimeFO> source) {
        final List<Integer> media = new ArrayList<>();
        for (final TimeFO medium : source) {
            media.add(mapper.map(medium, Integer.class));
        }

        return media;
    }

    /**
     * Returns genres.
     *
     * @param source source
     * @return genres
     */
    private List<GenreTO> convertIdList(final List<String> source) {
        final List<GenreTO> genres = new ArrayList<>();
        for (final String id : source) {
            genres.add(mapper.map(id, GenreTO.class));
        }

        return genres;
    }

    /**
     * Returns converted TO for movie to FO for movie.
     *
     * @param source TO for movie
     * @return converted TO for movie to FO for movie
     */
    private MovieFO convertMovieTO(final MovieTO source) {
        final MovieFO movie = new MovieFO();
        movie.setId(source.getId());
        movie.setCzechName(source.getCzechName());
        movie.setOriginalName(source.getOriginalName());
        movie.setYear(Integer.toString(source.getYear()));
        movie.setLanguage(source.getLanguage());
        movie.setSubtitles(source.getSubtitles());
        movie.setMedia(convertMediaLength(source.getMedia()));
        movie.setCsfd(source.getCsfd());
        if (source.getImdbCode() < 1) {
            movie.setImdb(false);
        } else {
            movie.setImdb(true);
            movie.setImdbCode(Integer.toString(source.getImdbCode()));
        }
        movie.setWikiCz(source.getWikiCz());
        movie.setWikiEn(source.getWikiEn());
        movie.setPicture(source.getPicture());
        movie.setNote(source.getNote());
        movie.setPosition(source.getPosition());
        movie.setGenres(convertGenres(source.getGenres()));

        return movie;
    }

    /**
     * Returns media.
     *
     * @param source source
     * @return media
     */
    private List<TimeFO> convertMediaLength(final List<Integer> source) {
        final List<TimeFO> media = new ArrayList<>();
        for (final Integer medium : source) {
            media.add(mapper.map(medium, TimeFO.class));
        }

        return media;
    }

    /**
     * Returns genres.
     *
     * @param source source
     * @return genres
     */
    private List<String> convertGenres(final List<GenreTO> source) {
        final List<String> genres = new ArrayList<>();
        for (final GenreTO genre : source) {
            genres.add(mapper.map(genre, String.class));
        }

        return genres;
    }

}
