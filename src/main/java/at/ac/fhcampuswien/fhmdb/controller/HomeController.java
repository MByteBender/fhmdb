package at.ac.fhcampuswien.fhmdb.controller;

import at.ac.fhcampuswien.fhmdb.api.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.SortState;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
        initializeState();
        initializeLayout();
        sortMovies(observableMovies);
    }

    public void initializeState() {
        //allMovies = Movie.initializeMovies();
        allMovies = MovieAPI.getAllMovies();
        observableMovies.clear();
        observableMovies.addAll(allMovies); // add all movies to the observable list
        sortState = SortState.NONE;
    }

    public void initializeLayout() {
        movieListView.setItems(observableMovies);   // set the items of the listview to the observable list
        movieListView.setCellFactory(movieListView -> new MovieCell()); // apply custom cells to the listview
        genreComboBox.getItems().addAll(Genre.values());
        genreComboBox.getSelectionModel().selectFirst();// add all genres to the combobox
        genreComboBox.setPromptText("Filter by Genre");
    }


    /** Filters Movies with searchterm
     * @param genreFilter
     * @param searchTerm
     * @param allMovies
     * @return ArrayList
     */
    public List<Movie> filterMovies(String genreFilter, String searchTerm, List<Movie> allMovies) {
        List<Movie> filteredMovies;

        if(allMovies == null) {
            throw new IllegalArgumentException("movies must not be null");
        }
        if (genreFilter.equals("NO_GENRE_FILTER")){
            filteredMovies= allMovies.stream()
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

    public void searchBtnClicked(ActionEvent actionEvent){
        String genreFilter = genreComboBox.getSelectionModel().getSelectedItem().toString();
        String searchterm = searchField.getText();
        observableMovies.setAll(filterMovies(genreFilter, searchterm, allMovies));
    }

    public void sortBtnClicked(ActionEvent actionEvent) {
        reverseMovies();
    }


}