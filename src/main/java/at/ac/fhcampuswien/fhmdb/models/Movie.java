package at.ac.fhcampuswien.fhmdb.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Movie {
    private String title;
    private String description;

    private List<Genre> genres;
    // TODO add more properties here

    public Movie(String title, String description, List<Genre>genres) {
        this.title = title;
        this.description = description;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public static List<Movie> initializeMovies(){
        List<Movie>  movies= new ArrayList<>();
        movies.add(new Movie(
                "The Matrix",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        movies.add(new Movie(
                "The Matrix 2",
                "A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.",
                Arrays.asList(Genre.ACTION, Genre.SCIENCE_FICTION)));
        movies.add(new Movie(
                "Forrest Gump",
                "The presidencies of Kennedy and Johnson, the Vietnam War, and the Watergate scandal, is seen through the eyes of an Alabama man with an IQ of 75.",
                Arrays.asList(Genre.DRAMA, Genre.ROMANCE, Genre.COMEDY)));
        movies.add(new Movie(
                "Pulp Fiction",
                "The lives of two mob hitmen, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                Arrays.asList(Genre.DRAMA, Genre.CRIME, Genre.THRILLER)));
        movies.add(new Movie(
                "Jurassic Park",
                "A pragmatic paleontologist visiting an almost complete theme park is tasked with protecting a couple of kids after a power failure causes the park's cloned dinosaurs to run loose.",
                Arrays.asList(Genre.ADVENTURE, Genre.SCIENCE_FICTION, Genre.THRILLER)));
//        movies.add (new Movie(
//                "The Silence of the Lambs",
//                "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.", Arrays.asList(new Genre("CRIME"), new Genre("DRAMA"), new Genre("THRILLER"))));
//        movies.add(new Movie(
//                "The Lion King",
//                "Lion prince Simba and his father are targeted by his bitter uncle, who wants to ascend the throne himself.", Arrays.asList(new Genre("ANIMATION"), new Genre("ADVENTURE"), new Genre("DRAMA"))));
//        movies.add (new Movie(
//                "Titanic",
//                "A seventeen-year-old aristocrat falls in love with a kind but poor artist aboard the luxurious, ill-fated R.M.S. Titanic.", Arrays.asList(new Genre("DRAMA"), new Genre("ROMANCE"), new Genre("HISTORY"))));
//        movies.add(new Movie(
//                "The Godfather",
//                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.", Arrays.asList(new Genre("DRAMA"), new Genre("CRIME"))));
//        movies.add(new Movie(
//                "The Dark Knight",
//                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.", Arrays.asList(new Genre("ACTION"), new Genre("CRIME"), new Genre("DRAMA"))));
//        movies.add(new Movie(
//                "The Lord of the Rings: The Fellowship of the Ring",
//                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", Arrays.asList(new Genre("ACTION"), new Genre("ADVENTURE"), new Genre("FANTASY"))));
//        movies.add(new Movie(
//                "Inception",
//                "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.", Arrays.asList(new Genre("ACTION"), new Genre("THRILLER"), new Genre("SCIENCE_FICTION"))));
//        movies.add(new Movie(
//                "The Shawshank Redemption",
//                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.", Arrays.asList(new Genre("DRAMA"), new Genre("CRIME"))));
//        movies.add(new Movie(
//                "Star Wars: Episode IV - A New Hope",
//                "Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a Wookiee and two droids to save the galaxy from the Empire's world-destroying battle station.", Arrays.asList(new Genre("ACTION"), new Genre("ADVENTURE"), new Genre("FANTASY"), new Genre("SCIENCE_FICTION"))));
//        movies.add(new Movie(
//                "The Avengers",
//                "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.", Arrays.asList(new Genre("ACTION"), new Genre("ADVENTURE"), new Genre("SCIENCE_FICTION"))));
//        movies.add(new Movie(
//                "The Social Network",
//                "Harvard student Mark Zuckerberg creates the social networking site that would become known as Facebook, but is later sued by two brothers who claimed he stole their idea, and the co-founder who was later squeezed out of the business.", Arrays.asList(new Genre("DRAMA"), new Genre("BIOGRAPHY"))));
//        movies.add(new Movie(
//                "The Grand Budapest Hotel",
//                "The adventures of Gustave H, a legendary concierge at a famous hotel from the fictional Republic of Zubrowka between the first and second World Wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.", Arrays.asList(new Genre("COMEDY"), new Genre("ADVENTURE"))));
        return movies;
    }
}
