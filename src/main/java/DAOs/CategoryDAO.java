package DAOs;

import Factories.ConnectionFactory;
import Models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private String tableName = "categories";
    private Connection connection = ConnectionFactory.getConnection();

    public CategoryDAO() {
        createTable();
    }

    public void createTable() {
        String sql = "CREATE SEQUENCE IF NOT EXISTS category_id_seq;";

        sql += "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "category_id BIGINT PRIMARY KEY DEFAULT nextval('category_id_seq')," +
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

    public Category getById(Long id) {
        String sql = "SELECT * FROM " + tableName +
                " WHERE category_id = ?;";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                return buildCategory(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public Category createCategory(Category category) {
        if (category != null) {
            String sql = "INSERT INTO " + tableName +
                    "(name)" +
                    "VALUES (?)";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                stmt.setString(1, category.getName());
                stmt.execute();

                ResultSet resultSet = stmt.getGeneratedKeys();

                while (resultSet.next()) {
                    category.setId(resultSet.getLong(1));
                }

                return category;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void editCategory(Category category) {
        if (category != null) {
            String sql = "UPDATE " + tableName + " SET " +
                    "name = ?" +
                    "WHERE category_id = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(sql);

                stmt.setString(1, category.getName());
                stmt.setLong(2, category.getId());

                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            String sql = "DELETE FROM " + tableName + " WHERE category_id = ?";

             try {
                 PreparedStatement stmt = connection.prepareStatement(sql);
                 stmt.setLong(1, id);

                 stmt.execute();
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
        }
    }

    public List<Category> listCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                categories.add(buildCategory(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categories;
    }

    private Category buildCategory(ResultSet resultSet) {
        try {
            Category category = new Category();

            category.setId(resultSet.getLong("category_id"));
            category.setBooks(new BookDAO().listBooksByCategory(category.getId()));
            category.setName(resultSet.getString("name"));

            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
