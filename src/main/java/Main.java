import DAOs.*;
import Views.*;

public class Main {
    private static void createTables() {
        new LibraryDAO().createTable();
        new CategoryDAO().createTable();
        new BookDAO().createTable();
    }

    public static void main(String[] args) {
        createTables();
        menu();
    }

    private static void bookMenu() {
        BookView view = new BookView();
        view.menu();
    }

    private static void categoryMenu() {
        CategoryView view = new CategoryView();
        view.menu();
    }

    private static void libraryMenu() {
        LibraryView view = new LibraryView();
        view.menu();
    }

    private static void menu() {
        MainView view = new MainView();
        view.menu();
    }
}
