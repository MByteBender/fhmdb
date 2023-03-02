package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.Genre;
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
    public ObservableList<Movie> getObservableMovies() {
        return observableMovies;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list

        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().add("No Genre Filter");
        genreComboBox.getItems().addAll(Genre.values()); //add all genres to comboBox


        searchBtn.setOnAction(actionEvent -> {
                    String filterElement = "";
                    if (genreComboBox.getSelectionModel().getSelectedItem() != null) {
                        filterElement = genreComboBox.getSelectionModel().getSelectedItem().toString();
                    }
                    String searchterm = searchField.getText();
                    observableMovies.setAll(filterMovies(filterElement, searchterm));
                }
        );


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        // TODO sorting after filter does not work fix!! - own TODO Tristan
        sortBtn.setOnAction(actionEvent -> {
            if(sortBtn.getText().equals("Sort (asc)")) {


                sortMovies(sortBtn.getText());

                // TODO sort observableMovies ascending
                sortBtn.setText("Sort (desc)");
            } else {

                sortMovies(sortBtn.getText());
                // TODO sort observableMovies descending
                sortBtn.setText("Sort (asc)");
            }
        });
    }


    public List<Movie> filterMovies(String filterElement, String searchTerm) {
        List<Movie> filteredMovies = new ArrayList<>();

        if (filterElement.equals("No Genre Filter") || genreComboBox.getPromptText().equals("Filter by Genre")){
            for (Movie movie : allMovies){
                if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())){
                    filteredMovies.add(movie);
                }
            }
        } else {
            for (Movie movie : allMovies) {
                List<Genre> genres = movie.getGenres();
                for (Genre genre : genres) {
                    if (genre.toString().toUpperCase().contains(filterElement) &&
                            (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                                    movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))) {
                        filteredMovies.add(movie);
                    }
                }
            }
        }
        return filteredMovies;
    }
    public void sortMovies(String sortText){
        if (sortBtn.getText().equals("Sort (asc)")){
            observableMovies.sort(Comparator.comparing(Movie::getTitle));
        } else if (sortBtn.getText().equals("Sort (desc)")){
            observableMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
        }



//        sortState = SortState.ASCENDING;
    }

}