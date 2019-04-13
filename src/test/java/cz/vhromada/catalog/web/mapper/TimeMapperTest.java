package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.web.common.TimeUtils;
import cz.vhromada.catalog.web.fo.TimeFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Integer} and {@link TimeFO}.
 *
 * @author Vladimir Hromada
 */
class TimeMapperTest {

    /**
     * Mapper for time
     */
    private TimeMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(TimeMapper.class);
    }

    /**
     * Test method for {@link TimeMapper#map(Integer)}.
     */
    @Test
    void map() {
        final Integer length = 100;

        final TimeFO time = mapper.map(length);

        TimeUtils.assertTimeDeepEquals(time, length);
    }

    /**
     * Test method for {@link TimeMapper#map(Integer)} with null length.
     */
    @Test
    void map_NullLength() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link TimeMapper#mapBack(TimeFO)}.
     */
    @Test
    void mapBack() {
        final TimeFO time = TimeUtils.getTimeFO();

        final Integer length = mapper.mapBack(time);

        TimeUtils.assertTimeDeepEquals(time, length);
    }

    /**
     * Test method for {@link TimeMapper#mapBack(TimeFO)} with null time.
     */
    @Test
    void mapBack_NullTime() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
