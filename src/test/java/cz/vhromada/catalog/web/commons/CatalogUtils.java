package cz.vhromada.catalog.web.commons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * A class represents utility class for tests.
 *
 * @author Vladimir Hromada
 */
public final class CatalogUtils {

    /**
     * ID
     */
    public static final Integer ID = 1;

    /**
     * English Wikipedia
     */
    public static final String EN_WIKI = "enWiki";

    /**
     * Czech Wikipedia
     */
    public static final String CZ_WIKI = "czWiki";

    /**
     * Media
     */
    public static final Integer MEDIA = 10;

    /**
     * Length
     */
    public static final Integer LENGTH = 100;

    /**
     * Name
     */
    public static final String NAME = "Name";

    /**
     * Note
     */
    public static final String NOTE = "Note";

    /**
     * Number
     */
    public static final Integer NUMBER = 2;

    /**
     * Year
     */
    public static final Integer YEAR = 2000;

    /**
     * Position
     */
    public static final Integer POSITION = 0;

    /**
     * Creates a new instance of CatalogUtils.
     */
    private CatalogUtils() {
    }

    /**
     * Asserts IMDB code deep equals.
     *
     * @param expectedImdb     expected IMDB selection
     * @param expectedImdbCode expected IMDB code
     * @param actualImdbCode   actual IMDB code
     */
    public static void assertImdbCodeDeepEquals(final boolean expectedImdb, final String expectedImdbCode, final int actualImdbCode) {
        if (expectedImdb) {
            assertEquals(expectedImdbCode, Integer.toString(actualImdbCode));
        } else {
            assertEquals(-1, actualImdbCode);
        }
    }

    /**
     * Asserts IMDB code deep equals.
     *
     * @param expectedImdbCode expected IMDB code
     * @param actualImdb       actual IMDB selection
     * @param actualImdbCode   actual IMDB code
     */
    public static void assertImdbDeepEquals(final int expectedImdbCode, final boolean actualImdb, final String actualImdbCode) {
        if (expectedImdbCode < 1) {
            assertFalse(actualImdb);
            assertNull(actualImdbCode);
        } else {
            assertTrue(actualImdb);
            assertEquals(Integer.toString(expectedImdbCode), actualImdbCode);
        }
    }

}
