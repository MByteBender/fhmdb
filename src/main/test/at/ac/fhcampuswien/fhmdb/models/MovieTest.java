package at.ac.fhcampuswien.fhmdb.models;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;



class MovieTest {



    @Test
    void checks_if_list_of_initialized_movies_is_not_empty() {

        // given
        List<Movie>  movies;

        // when
        movies = Movie.initializeMovies();

        // when then
        assertFalse(movies.isEmpty());
    }

}