package cz.vhromada.catalog.web.converters;

import java.util.ArrayList;
import java.util.List;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.fo.ShowFO;

import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;
import org.dozer.MappingException;

/**
 * A class represents converter between FO for show and TO for show.
 *
 * @author Vladimir Hromada
 */
public class ShowConverter implements MapperAware, CustomConverter {

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
        if (sourceFieldValue instanceof ShowFO && sourceClass == ShowFO.class && destinationClass == ShowTO.class) {
            return convertShowFO((ShowFO) sourceFieldValue);
        } else if (sourceFieldValue instanceof ShowTO && sourceClass == ShowTO.class && destinationClass == ShowFO.class) {
            return convertShowTO((ShowTO) sourceFieldValue);
        } else {
            throw new MappingException("Converter ShowConverter used incorrectly. Arguments passed in were:" + existingDestinationFieldValue + " and "
                    + sourceFieldValue);
        }
    }

    @Override
    public void setMapper(final Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Returns converted FO for show to TO for show.
     *
     * @param source FO for show
     * @return converted FO for show to TO for show
     */
    private ShowTO convertShowFO(final ShowFO source) {
        final ShowTO show = new ShowTO();
        show.setId(source.getId());
        show.setCzechName(source.getCzechName());
        show.setOriginalName(source.getOriginalName());
        show.setCsfd(source.getCsfd());
        show.setImdbCode(source.getImdb() ? Integer.valueOf(source.getImdbCode()) : -1);
        show.setWikiCz(source.getWikiCz());
        show.setWikiEn(source.getWikiEn());
        show.setPicture(source.getPicture());
        show.setNote(source.getNote());
        show.setPosition(source.getPosition());
        show.setGenres(convertIdList(source.getGenres()));

        return show;
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
     * Returns converted TO for show to FO for show.
     *
     * @param source TO for show
     * @return converted TO for show to FO for show
     */
    private ShowFO convertShowTO(final ShowTO source) {
        final ShowFO show = new ShowFO();
        show.setId(source.getId());
        show.setCzechName(source.getCzechName());
        show.setOriginalName(source.getOriginalName());
        show.setCsfd(source.getCsfd());
        if (source.getImdbCode() < 1) {
            show.setImdb(false);
        } else {
            show.setImdb(true);
            show.setImdbCode(Integer.toString(source.getImdbCode()));
        }
        show.setWikiCz(source.getWikiCz());
        show.setWikiEn(source.getWikiEn());
        show.setPicture(source.getPicture());
        show.setNote(source.getNote());
        show.setPosition(source.getPosition());
        show.setGenres(convertGenres(source.getGenres()));

        return show;
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
