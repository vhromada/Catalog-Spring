package cz.vhromada.catalog.web.converters;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.commons.ObjectGeneratorTest;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.web.fo.BookFO;
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
 * A class represents test for converter from {@link BookTO} to {@link BookFO}.
 *
 * @author Vladimir Hromada
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:testWebConvertersContext.xml")
public class BookTOToBookFOConverterTest extends ObjectGeneratorTest {

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
     * Test method for {@link Converter#convert(Object, Class)}.
     */
    @Test
    public void testConvert() {
        final BookTO bookTO = objectGenerator.generate(BookTO.class);
        bookTO.setLanguages(Arrays.asList(Language.CZ, Language.EN));
        final BookFO bookFO = converter.convert(bookTO, BookFO.class);
        DeepAsserts.assertNotNull(bookFO);
        DeepAsserts.assertEquals(bookTO, bookFO, "czech", "english", "languages", "bookCategory");
        for (final Language language : bookTO.getLanguages()) {
            switch (language) {
                case CZ:
                    assertTrue(bookFO.isCzech());
                    break;
                case EN:
                    assertTrue(bookFO.isEnglish());
                    break;
                default:
                    throw new IllegalArgumentException("Bad language");
            }
        }
    }

    /**
     * Test method for {@link Converter#convert(Object, Class)} with null argument.
     */
    @Test
    public void testConvertWithNullArgument() {
        assertNull(converter.convert(null, BookFO.class));
    }

}
