package cz.vhromada.catalog.web.converter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.web.common.CatalogUtils;
import cz.vhromada.catalog.web.common.GenreUtils;
import cz.vhromada.converter.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link Genre} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class GenreToIntegerConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer.
     */
    @Test
    public void convertGenre() {
        final Genre genre = GenreUtils.getGenre();

        final Integer result = converter.convert(genre, Integer.class);

        assertThat(result, is(notNullValue()));
        assertThat(result, is(genre.getId()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to integer with null argument.
     */
    @Test
    public void convertGenre_NullGenre() {
        assertThat(converter.convert(null, Integer.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity.
     */
    @Test
    public void convertInteger() {

        final Genre genre = converter.convert(CatalogUtils.ID, Genre.class);

        assertThat(genre, is(notNullValue()));
        assertThat(genre.getId(), is(CatalogUtils.ID));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to entity with null argument.
     */
    @Test
    public void convertInteger_NullInteger() {
        assertThat(converter.convert(null, Integer.class), is(nullValue()));
    }

}
