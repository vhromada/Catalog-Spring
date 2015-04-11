package cz.vhromada.catalog.web;

import cz.vhromada.catalog.web.converters.ConvertersSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A class represents test suite for all tests.
 *
 * @author Vladimir Hromada
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(ConvertersSuite.class)
public class CatalogSuite {
}
