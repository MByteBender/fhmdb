package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortState;
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
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXComboBox <Genre> genreComboBox;

    @FXML
    public JFXButton sortBtn;

    public List<Movie> allMovies = Movie.initializeMovies();
    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    public SortState sortState = SortState.NONE;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableMovies.addAll(allMovies);         // add dummy data to observable list
        sortMovies(observableMovies);


        // initialize UI stuff
        movieListView.setItems(observableMovies);   // set data of observable list to list view
        movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data

        // TODO add genre filter items with genreComboBox.getItems().addAll(...)
        genreComboBox.setPromptText("Filter by Genre");


        genreComboBox.getItems().addAll(Genre.values()); //add all genres to comboBox
        genreComboBox.getSelectionModel().selectFirst();


        searchBtn.setOnAction(actionEvent -> {
            String genreFilter = genreComboBox.getSelectionModel().getSelectedItem().toString();
            String searchterm = searchField.getText();
            observableMovies.setAll(filterMovies(genreFilter, searchterm, allMovies));
        });


        // TODO add event handlers to buttons and call the regarding methods
        // either set event handlers in the fxml file (onAction) or add them here

        // Sort button example:
        sortBtn.setOnAction(actionEvent -> {

            this.sortBtn.setText(reverseMovies());
        });
    }


    /** Filters Movies with searchterm
     * @param genreFilter
     * @param searchTerm
     * @param allMovies
     * @return ArrayList
     */
    public List<Movie> filterMovies(String genreFilter, String searchTerm, List<Movie> allMovies) {
        List<Movie> filteredMovies;

        if (genreFilter.equals("NO_GENRE_FILTER")){
            filteredMovies= allMovies
                    .stream()
                    .filter(movie -> movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            filteredMovies = allMovies
                    .stream()
                    .filter(movie -> movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                            movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase()))
                    .filter(movie -> movie.getGenres().contains(Genre.valueOf(genreFilter)))
                    .collect(Collectors.toList());

        }

        return filteredMovies;
    }


    /** Sorts Movies ascending
     * @param observableMovies
     * @return void
     */
    public void sortMovies(ObservableList<Movie> observableMovies){
        observableMovies.sort (Comparator.comparing(Movie::getTitle));
        sortState = SortState.ASCENDING;
    }


    /** reverses Movielist depending on SortState and
     * @return correct Sortstate String
     */
    public String reverseMovies() throws IllegalArgumentException {
        if (this.sortState == SortState.ASCENDING) {
            FXCollections.reverse(observableMovies);
            this.sortState = SortState.DESCENDING;
            return "Sort (asc)";
        } else if (this.sortState == SortState.DESCENDING) {
            FXCollections.reverse(observableMovies);
            this.sortState = SortState.ASCENDING;
            return "Sort (desc)";
        } else throw new IllegalArgumentException("Kein gültiger Sortstate "+ sortState.toString());
    }



}