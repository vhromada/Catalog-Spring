package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.web.common.CatalogUtils;
import cz.vhromada.catalog.web.common.GenreUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Genre} and {@link Integer}.
 *
 * @author Vladimir Hromada
 */
class GenreIdMapperTest {

    /**
     * Mapper for genre IDs
     */
    private GenreIdMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(GenreIdMapper.class);
    }

    /**
     * Test method for {@link GenreIdMapper#map(Genre)}.
     */
    @Test
    void map() {
        final Genre genre = GenreUtils.getGenre();

        final Integer id = mapper.map(genre);

        GenreUtils.assertGenreDeepEquals(genre, id);
    }

    /**
     * Test method for {@link GenreIdMapper#map(Genre)} with null genre.
     */
    @Test
    void map_NullGenre() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link GenreIdMapper#mapBack(Integer)}.
     */
    @Test
    void mapBack() {
        final Genre genre = mapper.mapBack(CatalogUtils.ID);

        GenreUtils.assertGenreDeepEquals(CatalogUtils.ID, genre);
    }

    /**
     * Test method for {@link GenreIdMapper#mapBack(Integer)} with null value.
     */
    @Test
    void mapBack_NullId() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
