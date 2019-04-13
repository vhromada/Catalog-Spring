package cz.vhromada.catalog.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.web.common.MovieUtils;
import cz.vhromada.catalog.web.fo.MovieFO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * A class represents test for mapper between {@link Movie} and {@link MovieFO}.
 *
 * @author Vladimir Hromada
 */
class MovieMapperTest {

    /**
     * Mapper for movies
     */
    private MovieMapper mapper;

    /**
     * Initializes mapper.
     */
    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(MovieMapper.class);
    }

    /**
     * Test method for {@link MovieMapper#map(Movie)}.
     */
    @Test
    void map() {
        final Movie movie = MovieUtils.getMovie();

        final MovieFO movieFO = mapper.map(movie);

        MovieUtils.assertMovieDeepEquals(movie, movieFO);
    }

    /**
     * Test method for {@link MovieMapper#map(Movie)} with null movie.
     */
    @Test
    void map_NullMovie() {
        assertThat(mapper.map(null)).isNull();
    }

    /**
     * Test method for {@link MovieMapper#mapBack(MovieFO)}.
     */
    @Test
    void mapBack() {
        final MovieFO movieFO = MovieUtils.getMovieFO();

        final Movie movie = mapper.mapBack(movieFO);

        MovieUtils.assertMovieDeepEquals(movieFO, movie);
    }

    /**
     * Test method for {@link MovieMapper#mapBack(MovieFO)} with null movie.
     */
    @Test
    void mapBack_NullMovie() {
        assertThat(mapper.mapBack(null)).isNull();
    }

}
