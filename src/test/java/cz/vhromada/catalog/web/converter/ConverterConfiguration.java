package cz.vhromada.catalog.web.converter;

import cz.vhromada.converters.orika.OrikaConfiguration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author vladimir.hromada
 */
@Configuration
@ComponentScan("cz.vhromada.catalog.web.converter")
@Import(OrikaConfiguration.class)
public class ConverterConfiguration {
}
