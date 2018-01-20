package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.web.common.GenreUtils;
import cz.vhromada.catalog.web.fo.GenreFO;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link GenreFO} to {@link Genre}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class GenreConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    void convertGenreFO() {
        final GenreFO genreFO = GenreUtils.getGenreFO();

        final Genre genre = converter.convert(genreFO, Genre.class);

        GenreUtils.assertGenreDeepEquals(genreFO, genre);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for genre.
     */
    @Test
    void convertGenreFO_NullGenreFO() {
        assertThat(converter.convert(null, Genre.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    void convertGenre() {
        final Genre genre = GenreUtils.getGenre();

        final GenreFO genreFO = converter.convert(genre, GenreFO.class);

        GenreUtils.assertGenreDeepEquals(genreFO, genre);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null genre.
     */
    @Test
    void convertGenre_NullGenre() {
        assertThat(converter.convert(null, GenreFO.class)).isNull();
    }

}
