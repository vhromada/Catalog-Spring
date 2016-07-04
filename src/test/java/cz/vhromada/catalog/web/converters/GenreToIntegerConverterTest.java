package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.web.commons.CatalogUtils;
import cz.vhromada.catalog.web.commons.GenreUtils;
import cz.vhromada.converters.Converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link GenreTO} to {@link Integer}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:webDozerMappingContext.xml")
public class GenreToIntegerConverterTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to integer.
     */
    @Test
    public void testConvertGenreTO() {
        final GenreTO genre = GenreUtils.getGenreTO();

        final Integer result = converter.convert(genre, Integer.class);

        assertNotNull(result);
        assertEquals(genre.getId(), result);
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from TO to integer with null argument.
     */
    @Test
    public void testConvertGenreTO_NullArgument() {
        assertNull(converter.convert(null, Integer.class));
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to TO.
     */
    @Test
    public void testConvertInteger() {

        final GenreTO genre = converter.convert(CatalogUtils.ID, GenreTO.class);

        assertNotNull(genre);
        assertEquals(CatalogUtils.ID, genre.getId());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} from integer to TO with null argument.
     */
    @Test
    public void testConvertInteger_NullArgument() {
        assertNull(converter.convert(null, Integer.class));
    }

}
