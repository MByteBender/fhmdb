package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.Exceptions.MovieApiException;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import okhttp3.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;

public class MovieAPI {
    public static final String DELIMITER = "&";
    private static final String URL = "http://prog2.fh-campuswien.ac.at/movies"; // https if certificates work
//    private static final String URL = "http://localhost:8080/movies"; // https if certificates work

    private static final OkHttpClient client = new OkHttpClient();

    private String buildUrl(UUID id) {
        StringBuilder url = new StringBuilder(URL);
        if (id != null) {
            url.append("/").append(id);
        }
        return url.toString();
        
    }

    private static String buildUrl(String query, Genre genre, String releaseYear, String ratingFrom) {
        // !Test URL WITH PRINT
       /* String testUrl = new MovieAPIRequestBuilder(URL)
                .query(query)
                .genre(genre)
                .releaseYear(releaseYear)
                .ratingFrom(ratingFrom)
                .build();

        System.out.println("Tets URL: "+ testUrl);
*/
        return new MovieAPIRequestBuilder(URL)
                .query(query)
                .genre(genre)
                .releaseYear(releaseYear)
                .ratingFrom(ratingFrom)
                .build();





//        StringBuilder url = new StringBuilder(URL);
//
//        if ( (query != null && !query.isEmpty()) ||
//                genre != null || releaseYear != null || ratingFrom != null) {
//
//            url.append("?");
//
//            // check all parameters and add them to the url
//            if (query != null && !query.isEmpty()) {
//                url.append("query=").append(query).append(DELIMITER);
//            }
//            if (genre != null) {
//                url.append("genre=").append(genre).append(DELIMITER);
//            }
//            if (releaseYear != null) {
//                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
//            }
//            if (ratingFrom != null) {
//                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
//            }
//        }
//
//        System.out.println(url.toString());
//        return url.toString();
    }

    public static List<Movie> getAllMovies() throws MovieApiException {
        return getAllMovies(null, null, null, null);
    }

    public static List<Movie> getAllMovies(String query, Genre genre, String releaseYear, String ratingFrom){
        String url = buildUrl(query, genre, releaseYear, ratingFrom);
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")  // needed for the server to accept the request
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = Objects.requireNonNull(response.body()).string();
            Gson gson = new Gson();
            Movie[] movies = gson.fromJson(responseBody, Movie[].class);

            return Arrays.asList(movies);
        } catch (IOException e) {
            new MovieApiException("Unexpected error in fetching the movies from the API. Check if you have internet connection!");
        }
        return new ArrayList<>();
    }

    public Movie requestMovieById(UUID id){
        String url = buildUrl(id);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson gson = new Gson();
            return gson.fromJson(response.body().string(), Movie.class);
        } catch (Exception e) {
            System.err.println(this.getClass() + ": http status not ok");
        }

        return null;
    }
}

