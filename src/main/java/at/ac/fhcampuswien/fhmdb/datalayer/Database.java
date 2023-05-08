package at.ac.fhcampuswien.fhmdb.datalayer;


import at.ac.fhcampuswien.fhmdb.Exceptions.DatabaseException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {
    public static final String DB_URL = "jdbc:h2:file: ./db/moviesDB";
    public static final String username = "user";
//    public static final String username = "use"; // test Error
    public static final String password = "pass";

    private static ConnectionSource connectionSource;

    private Dao<WatchlistEntity, Long> dao;

    private static Database instance;

    private Database() {//damit man von außerhalb keine Datenbank krerien kann
        try {
            createConnectionSource();
            dao = DaoManager.createDao(connectionSource, WatchlistEntity.class);
            createTables();
        } catch (SQLException e) {
            new DatabaseException("Unexpected error in fetching elements from the database");
        }

    }

    public static Database getInstance()  { //damit nur eine instanz der database existiert
        if(instance == null)
        {
            instance = new Database();
        }

        return instance;
    }

    public Dao<WatchlistEntity, Long> getDao()
    {
        return dao;
    }

    public  ConnectionSource getConnectionSource()
    {
        return connectionSource;
    }

    private static void createConnectionSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DB_URL, username, password);
    }

    private void createTables() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, WatchlistEntity.class);
    }

//    public void testDB() throws SQLException {
////        WatchlistEntity movie = new WatchlistEntity("aa", "OK", "ok", "YES", 2002, "OK", 230, 10); Test database
//        dao.create(movie);
//    }
}
