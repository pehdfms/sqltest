package DAOs;

import Factories.ConnectionFactory;
import Models.Book;
import Models.Category;
import Models.Library;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private String tableName = "books";
    private Connection connection = ConnectionFactory.getConnection();

    public BookDAO() {
        createTable();
    }

    public void createTable() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS book_id_seq;";

        sql += "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "book_id BIGINT PRIMARY KEY DEFAULT nextval('book_id_seq')," +
                "library_id BIGINT," +
                "category_id BIGINT," +
                "name TEXT NOT NULL," +
                "CONSTRAINT category_id " +
                    "FOREIGN KEY (category_id)" +
                    "REFERENCES categories(category_id), " +
                "CONSTRAINT library_id " +
                    "FOREIGN KEY (library_id)" +
                    "REFERENCES libraries(library_id)" +
                ");";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book getById(Long id) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE book_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return buildBook(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public ResultSet getResultById(Long id) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE book_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return resultSet;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Book createBook(Book book, Category category, Library library) {
        if (book != null) {
            String sql = "INSERT INTO " + tableName +
                    "(name, category_id, library_id)" +
                    "VALUES (?, ?, ?)";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, book.getName());
                stmt.setLong(2, category.getId());
                stmt.setLong(3, library.getId());
                stmt.execute();

                ResultSet resultSet = stmt.getGeneratedKeys();

                while (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                }

                return book;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void editBook(Book book, Category category, Library library) {
        if (book != null) {
            String sql = "UPDATE " + tableName + " SET " +
                    "name = ?, category_id = ?, library_id = ?" +
                    "WHERE book_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, book.getName());
                stmt.setLong(2, category.getId());
                stmt.setLong(3, library.getId());
                stmt.setLong(4, book.getId());

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            String sql = "DELETE FROM " + tableName + " WHERE book_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setLong(1, id);

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<Book> listBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public Category getCategory(Long id) {
        try {
            ResultSet resultSet = getResultById(id);
            return new CategoryDAO().getById(resultSet.getLong("category_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Library getLibrary(Long id) {
        try {
            ResultSet resultSet = getResultById(id);
            return new LibraryDAO().getById(resultSet.getLong("library_id"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> listBooksByCategory(Long id) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName +
                       " WHERE category_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    public List<Book> listBooksByLibrary(Long id) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName +
                " WHERE library_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                books.add(buildBook(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    private Book buildBook(ResultSet resultSet) {
        try {
            Book book = new Book();

            book.setId(resultSet.getLong("book_id"));
            book.setName(resultSet.getString("name"));

            return book;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
