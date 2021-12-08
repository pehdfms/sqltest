package DAOs;

import Factories.ConnectionFactory;
import Models.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    private String tableName = "movies";
    private Connection connection = ConnectionFactory.getConnection();

    public MovieDAO() {
        createTable();
    }

    public void createTable() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS movie_id_seq;";

        sql += "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "movie_id BIGINT PRIMARY KEY DEFAULT nextval('movie_id_seq')," +
                "name TEXT NOT NULL," +
                "category TEXT," +
                "duration TEXT);";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Movie getById(Long id) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE movie_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return buildMovie(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Movie createMovie(Movie movie) {
        if (movie != null) {
            String sql = "INSERT INTO " + tableName +
                    "(name, category, duration)" +
                    "VALUES (?, ?, ?)";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                setMovie(stmt, movie);
                stmt.execute();

                ResultSet resultSet = stmt.getGeneratedKeys();

                while (resultSet.next()) {
                    movie.setId(resultSet.getLong(1));
                }

                return movie;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void editMovie(Movie movie) {
        if (movie != null) {
            String sql = "UPDATE " + tableName + " SET " +
                    "name = ?, category = ?, duration = ? " +
                    "WHERE movie_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                setMovie(stmt, movie);
                stmt.setLong(4, movie.getId());

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            String sql = "DELETE FROM " + tableName + " WHERE movie_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setLong(1, id);

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public List<Movie> listMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                movies.add(buildMovie(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return movies;
    }

    private void setMovie(PreparedStatement stmt, Movie movie) {
        try {
            stmt.setString(1, movie.getName());
            stmt.setString(2, movie.getCategory());
            stmt.setString(3, movie.getDuration());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Movie buildMovie(ResultSet resultSet) {
        try {
            Movie movie = new Movie();

            movie.setId(resultSet.getLong("movie_id"));
            movie.setName(resultSet.getString("name"));
            movie.setCategory(resultSet.getString("category"));
            movie.setDuration(resultSet.getString("duration"));

            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

























}
