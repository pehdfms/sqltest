package Controllers;

import DAOs.BookDAO;
import DAOs.CategoryDAO;
import Models.Book;
import Models.Category;

import java.util.List;

public class CategoryController {
    CategoryDAO dao = new CategoryDAO();

    public CategoryController() {
    }

    public Category getById(Long id) {
        return dao.getById(id);
    }

    public Category createCategory(Category category) {
        return dao.createCategory(category);
    }

    public void editCategory(Category category) {
        dao.editCategory(category);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void deleteCategory(Category category) {
        deleteById(category.getId());
    }

    public List<Category> listCategories() {
        return dao.listCategories();
    }

    public List<Book> listBooksByCategory(Category category) {
        return new BookDAO().listBooksByCategory(category.getId());
    }
}
