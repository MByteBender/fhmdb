package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.controller.HomeController;
import at.ac.fhcampuswien.fhmdb.controller.WatchlistViewController;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;

@FunctionalInterface
public interface ClickEventHandler<T> {

//    public void onClick(T t);
    public void onClick(T t, WatchlistViewController watchlistViewController);
}
