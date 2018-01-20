package cz.vhromada.catalog.web.converter;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.web.common.CatalogUtils;
import cz.vhromada.catalog.web.common.GenreUtils;
import cz.vhromada.converter.Converter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * A class represents test for converter from {@link Genre} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
class GenreToIntegerConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer.
     */
    @Test
    void convertGenre() {
        final Genre genre = GenreUtils.getGenre();

        final Integer result = converter.convert(genre, Integer.class);

        GenreUtils.assertGenreDeepEquals(genre, result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer with null argument.
     */
    @Test
    void convertGenre_NullGenre() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity.
     */
    @Test
    void convertInteger() {
        final Genre genre = converter.convert(CatalogUtils.ID, Genre.class);

        GenreUtils.assertGenreDeepEquals(CatalogUtils.ID, genre);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity with null argument.
     */
    @Test
    void convertInteger_NullInteger() {
        assertThat(converter.convert(null, Integer.class)).isNull();
    }

}
