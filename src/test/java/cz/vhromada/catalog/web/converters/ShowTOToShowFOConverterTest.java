package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.web.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.converters.Converter;
import cz.vhromada.generator.ObjectGenerator;
import cz.vhromada.test.DeepAsserts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A class represents test for converter from {@link ShowTO} to {@link ShowFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class ShowTOToShowFOConverterTest extends ObjectGeneratorTest {

    /**
     * Instance of {@link Converter}
     */
    @Autowired
    @Qualifier("webDozerConverter")
    private Converter converter;

    /**
     * Instance of {@link ObjectGenerator}
     */
    @Autowired
    private ObjectGenerator objectGenerator;

    /**
     * Test method for {@link Converter#convert(Object, Class)} with selected IMDB code.
     */
    @Test
    public void testConvertWithSelectedImdbCode() {
        final ShowTO showTO = objectGenerator.generate(ShowTO.class);
        showTO.setImdbCode(objectGenerator.generate(Integer.class));

        final ShowFO showFO = converter.convert(showTO, ShowFO.class);

        DeepAsserts.assertNotNull(showFO);
        DeepAsserts.assertEquals(showTO, showFO, "imdbCode", "imdb", "genres");
        assertTrue(showFO.getImdb());
        DeepAsserts.assertEquals(Integer.toString(showTO.getImdbCode()), showFO.getImdbCode());
        assertGenresDeepEquals(showTO.getGenres(), showFO.getGenres());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with not selected IMDB code.
     */
    @Test
    public void testConvertWithNotSelectedImdbCode() {
        final ShowTO showTO = objectGenerator.generate(ShowTO.class);
        showTO.setImdbCode(-1);

        final ShowFO showFO = converter.convert(showTO, ShowFO.class);

        DeepAsserts.assertNotNull(showFO, "imdbCode");
        DeepAsserts.assertEquals(showTO, showFO, "imdbCode", "imdb", "genres");
        assertFalse(showFO.getImdb());
        assertNull(showFO.getImdbCode());
        assertGenresDeepEquals(showTO.getGenres(), showFO.getGenres());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, ShowFO.class));
    }

    /**
     * Assert genres deep equals.
     *
     * @param expected expected genres
     * @param actual   actual genres
     */
    private static void assertGenresDeepEquals(final List<GenreTO> expected, final List<String> actual) {
        DeepAsserts.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            DeepAsserts.assertEquals(Integer.toString(expected.get(i).getId()), actual.get(i));
        }
    }

}
