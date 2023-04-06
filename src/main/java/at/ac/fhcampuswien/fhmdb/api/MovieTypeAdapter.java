package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MovieTypeAdapter extends TypeAdapter<Movie> {

    // write must be overriden
    @Override
    public void write(JsonWriter jsonWriter, Movie movie) throws IOException {
        throw new UnsupportedOperationException("Serialization is not supported");
    }

    // when next token is Array method reads array
    private List<String> readJsonArray(JsonReader jsonReader) throws IOException {
        List<String> jsonList = new ArrayList<>();
        jsonReader.beginArray(); //you expect a beginn Array when not throw IOExeption
        if (jsonReader.peek() == JsonToken.END_ARRAY) { //peek look at next token
            return jsonList;
        }
        while (true) {
            String temp = jsonReader.nextString();
            jsonList.add(temp);
            if (jsonReader.peek() == JsonToken.END_ARRAY){
                break;
            }
        }
        jsonReader.endArray();
        return jsonList;
    }

    // reads json that is returend from API
    @Override
    public Movie read(JsonReader jsonReader) throws IOException {
        String title = "";
        String description = "";
        List<Genre> genres = new ArrayList<>();
        int releaseYear = 0;
        String id = "";
        String imgURL = "";
        int lengthInMinutes = 0;
        List<String> directors = new ArrayList<>();
        List<String> writers = new ArrayList<>();
        List<String> mainCast = new ArrayList<>();
        double rating = 0.0;
        jsonReader.beginObject();
        while(jsonReader.hasNext()) {
            String name = jsonReader.nextName(); // get key an set value correct to appropiate key
            switch (name) {
                case "id" -> id = jsonReader.nextString();
                case "title" -> title = jsonReader.nextString();
                case "genres" -> {
                    for (String tempString : this.readJsonArray(jsonReader)) {
                        genres.add(Genre.valueOf(tempString));
                    }
                }
                case "releaseYear" -> releaseYear = jsonReader.nextInt();
                case "description" -> description = jsonReader.nextString();
                case "imgUrl" -> imgURL = jsonReader.nextString();
                case "lengthInMinutes" -> lengthInMinutes = jsonReader.nextInt();
                case "directors" -> directors = this.readJsonArray(jsonReader);
                case "writers" -> writers = this.readJsonArray(jsonReader);
                case "mainCast" -> mainCast = this.readJsonArray(jsonReader);
                case "rating" -> rating = jsonReader.nextDouble();
            }
        }
        jsonReader.endObject();
        return new Movie(title, description, genres, releaseYear, id, imgURL, lengthInMinutes, directors, writers, mainCast, rating);
    }
}
