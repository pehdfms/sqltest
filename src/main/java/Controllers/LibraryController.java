package Controllers;

import DAOs.LibraryDAO;
import Models.Book;
import Models.Library;

import java.util.List;

public class LibraryController {
    LibraryDAO dao = new LibraryDAO();

    public LibraryController() {
    }

    public Library getById(Long id) {
        return dao.getById(id);
    }

    public Library createLibrary(Library Library) {
        return dao.createLibrary(Library);
    }

    public void editLibrary(Library Library) {
        dao.editLibrary(Library);
    }

    public void deleteById(Long id) {
        dao.deleteById(id);
    }

    public void deleteLibrary(Library Library) {
        deleteById(Library.getId());
    }

    public List<Library> listLibraries() {
        return dao.listLibraries();
    }

    public List<Book> listBooksByLibrary(Library library) {
        return dao.listBooksByLibrary(library.getId());
    }
}
