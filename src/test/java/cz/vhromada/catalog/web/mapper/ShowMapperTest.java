package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.web.common.ShowUtils;
import cz.vhromada.catalog.web.fo.ShowFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Show} and {@link ShowFO}.
 *
 * @author Vladimir Hromada
 */
class ShowMapperTest {

    /**
     * Mapper for shows
     */
    private ShowMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(ShowMapper.class);
    }

    /**
     * Test method for {@link ShowMapper#map(Show)}.
     */
    @Test
    void map() {
        final Show show = ShowUtils.getShow();

        final ShowFO showFO = mapper.map(show);

        ShowUtils.assertShowDeepEquals(show, showFO);
    }

    /**
     * Test method for {@link ShowMapper#map(Show)} with null show.
     */
    @Test
    void map_NullShow() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link ShowMapper#mapBack(ShowFO)}.
     */
    @Test
    void mapBack() {
        final ShowFO showFO = ShowUtils.getShowFO();

        final Show show = mapper.mapBack(showFO);

        ShowUtils.assertShowDeepEquals(showFO, show);
    }

    /**
     * Test method for {@link ShowMapper#mapBack(ShowFO)} with null show.
     */
    @Test
    void mapBack_NullShow() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
