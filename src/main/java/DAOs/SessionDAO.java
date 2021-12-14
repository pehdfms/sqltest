package DAOs;

import Factories.ConnectionFactory;
import Models.Movie;
import Models.Session;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO {
    private String tableName = "sessions";
    private Connection connection = ConnectionFactory.getConnection();

    public SessionDAO() {
        createTable();
    }

    public void createTable() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS session_id_seq;";

        sql += "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "session_id BIGINT PRIMARY KEY DEFAULT nextval('session_id_seq')," +
                "movie_id BIGINT," +
                "date TEXT NOT NULL," +
                "CONSTRAINT movie_id " +
                    "FOREIGN KEY (movie_id)" +
                    "REFERENCES movies(movie_id)" +
                ");";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Session getById(Long id) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE session_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return buildSession(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Session createSession(Session session) {
        if (session != null) {
            String sql = "INSERT INTO " + tableName +
                    "(date, movie_id)" +
                    "VALUES (?, ?)";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                setSession(stmt, session);
                stmt.execute();

                ResultSet resultSet = stmt.getGeneratedKeys();

                while (resultSet.next()) {
                    session.setId(resultSet.getLong(1));
                }

                return session;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void editSession(Session session) {
        if (session != null) {
            String sql = "UPDATE " + tableName + " SET " +
                    "movie_id = ?, date = ?" +
                    "WHERE session_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setLong(1, session.getPlaying().getId());
                stmt.setString(2, session.getStartDate());
                stmt.setLong(3, session.getId());

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            String sql = "DELETE FROM " + tableName + " WHERE session_id = ?";

             try {
                 PreparedStatement stmt = connection.prepareStatement(sql);
                 stmt.setLong(1, id);

                 stmt.execute();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
        }
    }

    public List<Session> listSessions() {
        List<Session> sessions = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                sessions.add(buildSession(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return sessions;
    }

    private void setSession(PreparedStatement stmt, Session session) {
        try {
            stmt.setString(1, session.getStartDate());
            stmt.setLong(2, session.getPlaying().getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Session buildSession(ResultSet resultSet) {
        try {
            Session session = new Session();

            session.setId(resultSet.getLong("session_id"));
            session.setStartDate(resultSet.getString("date"));

            MovieDAO movieDAO = new MovieDAO();
            Movie movie = movieDAO.getById(resultSet.getLong("movie_id"));

            session.setPlaying(movie);

            return session;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
