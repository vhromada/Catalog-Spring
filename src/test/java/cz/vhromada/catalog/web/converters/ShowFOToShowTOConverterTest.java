package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;

import java.util.Arrays;
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
 * A class represents test for converter from {@link ShowFO} to {@link ShowTO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class ShowFOToShowTOConverterTest extends ObjectGeneratorTest {

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
        final ShowFO showFO = newShowFO(true);

        final ShowTO showTO = converter.convert(showFO, ShowTO.class);

        DeepAsserts.assertNotNull(showTO);
        DeepAsserts.assertEquals(showFO, showTO, "imdbCode", "imdb", "genres");
        DeepAsserts.assertEquals(Integer.valueOf(showFO.getImdbCode()), showTO.getImdbCode());
        assertGenresDeepEquals(showFO.getGenres(), showTO.getGenres());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with not selected IMDB code.
     */
    @Test
    public void testConvertWithNotSelectedImdbCode() {
        final ShowFO showFO = newShowFO(false);

        final ShowTO showTO = converter.convert(showFO, ShowTO.class);

        DeepAsserts.assertNotNull(showTO);
        DeepAsserts.assertEquals(showFO, showTO, "imdbCode", "imdb", "genres");
        DeepAsserts.assertEquals(-1, showTO.getImdbCode());
        assertGenresDeepEquals(showFO.getGenres(), showTO.getGenres());
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, ShowTO.class));
    }

    /**
     * Returns {@link ShowFO}.
     *
     * @param imdbCode true if IMDB code is selected
     * @return {@link ShowFO}
     */
    private ShowFO newShowFO(final boolean imdbCode) {
        final ShowFO show = objectGenerator.generate(ShowFO.class);
        show.setGenres(Arrays.asList(Integer.toString(objectGenerator.generate(Integer.class)), Integer.toString(objectGenerator.generate(Integer.class))));
        show.setImdb(imdbCode);
        if (imdbCode) {
            show.setImdbCode(Integer.toString(objectGenerator.generate(Integer.class)));
        }

        return show;
    }

    /**
     * Assert genres deep equals.
     *
     * @param expected expected genres
     * @param actual   actual genres
     */
    private static void assertGenresDeepEquals(final List<String> expected, final List<GenreTO> actual) {
        DeepAsserts.assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            DeepAsserts.assertEquals(Integer.valueOf(expected.get(i)), actual.get(i).getId());
        }
    }

}
