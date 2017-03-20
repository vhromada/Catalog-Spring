package cz.vhromada.catalog.web;

import cz.vhromada.catalog.web.converter.ConverterSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A class represents test suite for all tests.
 *
 * @author Vladimir Hromada
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(ConverterSuite.class)
public class CatalogSuite {
}
