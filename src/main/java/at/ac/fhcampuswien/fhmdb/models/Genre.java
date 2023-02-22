package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.List;

public class Genre {
    private String genreName;

    public Genre(String genreName) {
        this.genreName = genreName;
    }

    public String getName() {
        return genreName;
    }

    public static List<Genre> getDefaultGenres() {
        List<Genre> defaultGenres = new ArrayList<>();
        defaultGenres.add(new Genre("ACTION"));
        defaultGenres.add(new Genre("ADVENTURE"));
        defaultGenres.add(new Genre("ANIMATION"));
        defaultGenres.add(new Genre("BIOGRAPHY"));
        defaultGenres.add(new Genre("COMEDY"));
        defaultGenres.add(new Genre("CRIME"));
        defaultGenres.add(new Genre("DRAMA"));
        defaultGenres.add(new Genre("DOCUMENTARY"));
        defaultGenres.add(new Genre("FAMILY"));
        defaultGenres.add(new Genre("FANTASY"));
        defaultGenres.add(new Genre("HISTORY"));
        defaultGenres.add(new Genre("HORROR"));
        defaultGenres.add(new Genre("MUSICAL"));
        defaultGenres.add(new Genre("MYSTERY"));
        defaultGenres.add(new Genre("ROMANCE"));
        defaultGenres.add(new Genre("SCIENCE_FICTION"));
        defaultGenres.add(new Genre("SPORT"));
        defaultGenres.add(new Genre("THRILLER"));
        defaultGenres.add(new Genre("WAR"));
        defaultGenres.add(new Genre("WESTERN"));
        return defaultGenres;
    }
}
