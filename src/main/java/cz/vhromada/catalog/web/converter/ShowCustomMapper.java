package cz.vhromada.catalog.web.converter;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.fo.ShowFO;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;

/**
 * A class represents custom mapper for show.
 *
 * @author Vladimir Hromada
 */
public class ShowCustomMapper extends CustomMapper<ShowFO, Show> {

    @Override
    public void mapAtoB(final ShowFO showMO, final Show show, final MappingContext context) {
        super.mapAtoB(showMO, show, context);

        show.setImdbCode(showMO.getImdb() ? Integer.parseInt(showMO.getImdbCode()) : -1);
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void mapBtoA(final Show show, final ShowFO showFO, final MappingContext context) {
        super.mapBtoA(show, showFO, context);

        if (show.getImdbCode() < 1) {
            showFO.setImdb(false);
            showFO.setImdbCode(null);
        } else {
            showFO.setImdb(true);
            showFO.setImdbCode(Integer.toString(show.getImdbCode()));
        }
    }

}
