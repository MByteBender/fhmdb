package at.ac.fhcampuswien.fhmdb;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    private static HomeController homeController;
    @BeforeAll
    static void inti() { homeController = new HomeController(); }

    @Test
    void click_on_Sort_button_when_it_shows_sortasc_sorts_movies_ascending(){
        //given

    }


//
//    @Test
//    void movies_and_observableMovies_are_equal(){
//        //GIVEN
//        homeController.initializeState();
//
//        //WHEN & THEN
//        assertEquals(homeController.allMovies, homeController.getObservableMovies());
//    }

}