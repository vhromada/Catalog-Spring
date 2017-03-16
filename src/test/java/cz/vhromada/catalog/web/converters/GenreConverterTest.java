package cz.vhromada.catalog.web.converters;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.web.common.GenreUtils;
import cz.vhromada.catalog.web.fo.GenreFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link GenreFO} to {@link Genre}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConverterConfiguration.class)
public class GenreConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity.
     */
    @Test
    public void convertGenreFO() {
        final GenreFO genreFO = GenreUtils.getGenreFO();

        final Genre genre = converter.convert(genreFO, Genre.class);

        GenreUtils.assertGenreDeepEquals(genreFO, genre);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to entity with null FO for genre.
     */
    @Test
    public void convertGenreFO_NullGenreFO() {
        assertThat(converter.convert(null, Genre.class), is(nullValue()));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO.
     */
    @Test
    public void convertGenre() {
        final Genre genre = GenreUtils.getGenre();

        final GenreFO genreFO = converter.convert(genre, GenreFO.class);

        GenreUtils.assertGenreDeepEquals(genreFO, genre);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from entity to FO with null genre.
     */
    @Test
    public void convertGenre_NullGenre() {
        assertThat(converter.convert(null, GenreFO.class), is(nullValue()));
    }

}
