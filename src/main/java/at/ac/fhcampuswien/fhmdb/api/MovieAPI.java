package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import okhttp3.*;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieAPI {

    public static final String DELIMITER = "&";
    OkHttpClient client = new OkHttpClient();
    final String BASE_URL = "http://localhost:8080";
    Gson gson = null;
    public MovieAPI() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Movie.class, new MovieTypeAdapter());
        this.gson = gsonBuilder.create();
    }
    public List<Movie> getFullMovieList() throws IOException {
        String url = BASE_URL + "/movies";
        Request request = new Request.Builder().url(url).removeHeader("User-Agent").addHeader("User-Agent", "http.agent").build();
        try(Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            Movie[] movies = this.gson.fromJson(responseString, Movie[].class);
            return Arrays.asList(movies);

        }
    }

    private  String buildUrl(String query, Genre genre, String releaseYear, String ratingFrom) {
        StringBuilder url = new StringBuilder(BASE_URL+ "/movies");

        if ( (query != null && !query.isEmpty()) ||
                genre != null || releaseYear != null || ratingFrom != null) {

            url.append("?");

            // check all parameters and add them to the url
            if (query != null && !query.isEmpty()) {
                url.append("query=").append(query).append(DELIMITER);
            }
            if (genre != null) {
                url.append("genre=").append(genre).append(DELIMITER);
            }
            if (releaseYear != null) {
                url.append("releaseYear=").append(releaseYear).append(DELIMITER);
            }
            if (ratingFrom != null) {
                url.append("ratingFrom=").append(ratingFrom).append(DELIMITER);
            }
        }

        System.out.println(url.toString());
        return url.toString();
    }
    public List<Movie> getFilteredMovieList(String query, Genre genre, String releaseYear, String ratingFrom) throws IOException {
        String url = buildUrl(query, genre, releaseYear, ratingFrom);
        Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", "http.agent")
                .build();


        System.out.println("request " + request);
        try (Response response = client.newCall(request).execute()) {
            String responseString = response.body().string();
            Movie[] movies = this.gson.fromJson(responseString, Movie[].class);
            return Arrays.asList(movies);
        }
    }
}
