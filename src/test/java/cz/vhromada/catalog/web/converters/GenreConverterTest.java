package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.web.fo.GenreFO;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link GenreFO} to {@link GenreTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class GenreConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO.
     */
    @Test
    public void testConvertGenreFO() {
        final GenreFO genreFO = new GenreFO();
        genreFO.setId(1);
        genreFO.setName("Name");
        genreFO.setPosition(0);

        final GenreTO genreTO = converter.convert(genreFO, GenreTO.class);

        assertNotNull(genreTO);
        assertEquals(genreFO.getId(), genreTO.getId());
        assertEquals(genreFO.getName(), genreTO.getName());
        assertEquals(genreFO.getPosition(), genreTO.getPosition());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from FO to TO with null argument.
     */
    @Test
    public void testConvertGenreFO_NullArgument() {
        assertNull(converter.convert(null, GenreTO.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO.
     */
    @Test
    public void testConvertGenreTO() {
        final GenreTO genreTO = new GenreTO();
        genreTO.setId(1);
        genreTO.setName("Name");
        genreTO.setPosition(0);

        final GenreFO genreFO = converter.convert(genreTO, GenreFO.class);

        assertNotNull(genreFO);
        assertEquals(genreTO.getId(), genreFO.getId());
        assertEquals(genreTO.getName(), genreFO.getName());
        assertEquals(genreTO.getPosition(), genreFO.getPosition());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to FO with null argument.
     */
    @Test
    public void testConvertGenreTO_NullArgument() {
        assertNull(converter.convert(null, GenreFO.class));
    }

}
