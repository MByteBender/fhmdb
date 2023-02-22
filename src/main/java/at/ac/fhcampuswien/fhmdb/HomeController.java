package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll("BIOGRAPHY", "GENRE", "TITLE");

        searchBtn.setOnAction(actionEvent -> {

            String selectedGenre = (String) genreComboBox.getSelectionModel().getSelectedItem();

            if (selectedGenre != null && selectedGenre.equals("BIOGRAPHY")) {
                String searchTerm = searchField.getText();
                List<Movie> results = new ArrayList<>();
                for (Movie movie : Movie.initializeMovies()) {
                    if (movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                        results.add(movie);
                    }
                }
                observableMovies.clear();
                observableMovies.addAll(results);

            } else if(selectedGenre != null && selectedGenre.equals("GENRE")){


            } else if(selectedGenre != null && selectedGenre.equals("TITLE")){
                System.out.println("TITLE");
                String searchTerm = searchField.getText();
                List<Movie> results = new ArrayList<>();
                for (Movie movie : Movie.initializeMovies()) {
                    if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                        results.add(movie);
                    }
                }
                observableMovies.clear();
                observableMovies.addAll(results);
        }});


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {


                observableMovies.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
//
                // TODO sort observableMovies ascending
                sortBtn.setText("Sort (desc)");
            } else {
                observableMovies.sort((o1, o2) -> o2.getTitle().compareTo(o1.getTitle()));

                // TODO sort observableMovies descending
                sortBtn.setText("Sort (asc)");
            }
        });



    }
}