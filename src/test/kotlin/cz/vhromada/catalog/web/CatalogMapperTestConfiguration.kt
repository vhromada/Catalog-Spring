package cz.vhromada.catalog.web

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * A class represents Spring configuration for tests.
 *
 * @author Vladimir Hromada
 */
@Configuration
@ComponentScan("cz.vhromada.catalog.web.mapper")
class CatalogMapperTestConfiguration
