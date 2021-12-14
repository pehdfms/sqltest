package Controllers;

import DAOs.BookDAO;
import Models.Book;
import Models.Category;
import Models.Library;

import java.util.List;

public class BookController {
    BookDAO dao = new BookDAO();

    public BookController() {
    }

    public Book getById(Long id) {
        return dao.getById(id);
    }

    public Category getCategory(Book book) {
        return dao.getCategory(book.getId());
    }

    public Library getLibrary(Book book) {
        return dao.getLibrary(book.getId());
    }

    public Book createBook(Book book, Category category, Library library) {
        return dao.createBook(book, category, library);
    }

    public void editBook(Book book, Category category, Library library) {
        dao.editBook(book, category, library);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void deleteBook(Book book) {
        deleteById(book.getId());
    }

    public List<Book> listBooks() {
        return dao.listBooks();
    }
}
