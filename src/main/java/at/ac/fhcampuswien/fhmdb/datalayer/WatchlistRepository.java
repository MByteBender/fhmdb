package at.ac.fhcampuswien.fhmdb.datalayer;

import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {
    Dao<WatchlistEntity, Long> dao;

    public WatchlistRepository() throws DatabaseException {
        this.dao = Database.getInstance().getDao(); //holt dao
    }
    public List<WatchlistEntity> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public void addToWatchlist(Movie movie) throws SQLException {
        String title = movie.getTitle().replace("'", "''");
        if (dao.queryForEq("title", title).isEmpty()) { //wenn titel nicht exisitert wird er der Databse hinzgefügt
            dao.create(movieToEntity(movie));
            System.out.println("Added " + movie.getTitle() + " to Watchlist");
        }
    }

    public void removeFromWatchlist(Movie movie) throws SQLException {
        String title = movie.getTitle().replace("'", "''");
        List<WatchlistEntity> movies = dao.queryForEq("title", title); //nimm titel wähle diesen Eintrag
        if (!movies.isEmpty()) {
            dao.delete(movies);
            System.out.println("Deleted " + movie.getTitle() + " from Watchlist");
        }
    }


    public WatchlistEntity movieToEntity(Movie movie) // konvertiert movie in watchlistentity und wird übers dao in Databse gepackt
    {
        return new WatchlistEntity(movie.getId(), movie.getTitle(), movie.getDescription(), WatchlistEntity.genresToString(movie.getGenres()), movie.getReleaseYear(), movie.getImgUrl(), movie.getLengthInMinutes(), movie.getRating());
    }

}
