package cz.vhromada.catalog.web.converter;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.fo.ShowFO;
import cz.vhromada.converters.orika.MapperConfig;

import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Component;

/**
 * A class represents mapper configuration for show.
 *
 * @author Vladimir Hromada
 */
@Component("showMapper")
public class ShowMapper implements MapperConfig {

    @Override
    public void config(final MapperFactory mapperFactory) {
        mapperFactory.classMap(ShowFO.class, Show.class)
                .exclude("imdbCode")
                .byDefault()
                .customize(new ShowCustomMapper())
                .register();
    }

}
