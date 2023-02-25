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
    private List<Movie> searchResults = new ArrayList<>();

    int count = 0;
    private ObservableList<Movie> filteredMovies = FXCollections.observableArrayList();

    private final ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes

    private final ObservableList<Movie> currentMovies = FXCollections.observableArrayList(allMovies);
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
        genreComboBox.getItems().addAll("BIOGRAPHY", "GENRE", "TITLE");

        searchBtn.setOnAction(actionEvent -> {



                    String filterElement = (String) genreComboBox.getSelectionModel().getSelectedItem();

                    filterMovies(filterElement);

//            if (searchField.getText().isEmpty()) {
//                observableMovies.addAll(allMovies);
//
//
//            } else if (filterElement != null && filterElement.equals("BIOGRAPHY")) {
//                String searchTerm = searchField.getText();
//                List<Movie> results = new ArrayList<>();
//                for (Movie movie : Movie.initializeMovies()) {
//                    if (movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
//                        results.add(movie);
//                    }
//                }
//                observableMovies.clear();
//                observableMovies.addAll(results);
//
//            } else if(filterElement != null && filterElement.equals("GENRE")){
//                String searchTerm = searchField.getText();
//                List<Movie> results = new ArrayList<>();
//                for (Movie movie : Movie.initializeMovies()) {
//                    if (movie.getGenres().contains(searchTerm)) {
//                        results.add(movie);
//                    }
//                }
//                observableMovies.clear();
//                observableMovies.addAll(results);
//
//
//            } else if(filterElement != null && filterElement.equals("TITLE")){
//                System.out.println("TITLE");
//                String searchTerm = searchField.getText();
//                List<Movie> results = new ArrayList<>();
//                for (Movie movie : Movie.initializeMovies()) {
//                    if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
//                        results.add(movie);
//                    }
//                }
//                observableMovies.clear();
//                observableMovies.addAll(results);
//        }
                }
        );


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


    public void filterMovies(String filterElement) {
//
//
        if (searchField.getText().isEmpty()) {
            filteredMovies.setAll(allMovies);
        } else if (filterElement != null && filterElement.equals("BIOGRAPHY")) {
            String searchTerm = searchField.getText();
            List<Movie> results = new ArrayList<>();
            for (Movie movie : allMovies) {
                if (movie.getDescription().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(movie);
                }
            }
            filteredMovies.setAll(results);
            observableMovies.addAll(filteredMovies);
            movieListView.setItems(filteredMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell());

        } else if (filterElement != null && filterElement.equals("GENRE")) {
            String searchTerm = searchField.getText();
            List<Movie> results = new ArrayList<>();
            for (Movie movie : allMovies) {
                if (movie.getGenres().contains(searchTerm)) {
                    results.add(movie);
                }
            }
            filteredMovies.setAll(results);
            observableMovies.addAll(filteredMovies);
            movieListView.setItems(filteredMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell());
        } else if (filterElement != null && filterElement.equals("TITLE")) {
            String searchTerm = searchField.getText();
            List<Movie> results = new ArrayList<>();
            for (Movie movie : allMovies) {
                if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                    results.add(movie);
                }
            }

            System.out.println("Durchgang: "+ count);
            count ++;
            filteredMovies.setAll(results);
            observableMovies.addAll(filteredMovies);
            movieListView.setItems(filteredMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell());

        }

        movieListView.setItems(filteredMovies);

        movieListView.setCellFactory(movieListView -> new MovieCell());
    }
//
//
//    public void sortMovies(){
//        observableMovies.sort(Comparator.comparing(Movie::getTitle));
//        sortState = SortState.ASCENDING;
//    }

}