package DAOs;

import Factories.ConnectionFactory;
import Models.Book;
import Models.Library;
import Models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {
    private String tableName = "libraries";
    private Connection connection = ConnectionFactory.getConnection();

    public LibraryDAO() {
        createTable();
    }

    public void createTable() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS library_id_seq;";

        sql += "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "library_id BIGINT PRIMARY KEY DEFAULT nextval('library_id_seq')," +
                "name TEXT NOT NULL" +
                ");";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Library getById(Long id) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE library_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return buildLibrary(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Library createLibrary(Library library) {
        if (library != null) {
            String sql = "INSERT INTO " + tableName +
                    "(name)" +
                    "VALUES (?)";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, library.getName());
                stmt.execute();

                ResultSet resultSet = stmt.getGeneratedKeys();

                while (resultSet.next()) {
                    library.setId(resultSet.getLong(1));
                }

                return library;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void editLibrary(Library library) {
        if (library != null) {
            String sql = "UPDATE " + tableName + " SET " +
                    "name = ? " +
                    "WHERE library_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, library.getName());
                stmt.setLong(2, library.getId());

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            String sql = "DELETE FROM " + tableName + " WHERE library_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setLong(1, id);

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Library> listLibraries() {
        List<Library> librarys = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                librarys.add(buildLibrary(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return librarys;
    }

    public List<Book> listBooksByLibrary(Long id) {
        return new BookDAO().listBooksByLibrary(id);
    }

    private Library buildLibrary(ResultSet resultSet) {
        try {
            Library library = new Library();

            library.setId(resultSet.getLong("library_id"));
            library.setName(resultSet.getString("name"));

            library.setBooks(listBooksByLibrary(library.getId()));

            return library;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
