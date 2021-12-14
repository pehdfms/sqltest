package Controllers;

import DAOs.MovieDAO;
import Models.Movie;

import java.util.List;

public class MovieController {
    MovieDAO dao = new MovieDAO();

    public MovieController() {
    }

    public Movie getById(Long id) {
        return dao.getById(id);
    }

    public Movie createMovie(Movie movie) {
        return dao.createMovie(movie);
    }

    public void editMovie(Movie movie) {
        dao.editMovie(movie);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void deleteSession(Movie movie) {
        deleteById(movie.getId());
    }

    public List<Movie> listMovies() {
        return dao.listMovies();
    }
}
