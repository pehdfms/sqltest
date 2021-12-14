import DAOs.MovieDAO;
import DAOs.SessionDAO;
import Views.MovieView;
import Views.SessionView;

public class Main {
    private static void createTables() {
        MovieDAO movieDAO = new MovieDAO();
        movieDAO.createTable();

        SessionDAO sessionDAO = new SessionDAO();
        sessionDAO.createTable();
    }

    public static void main(String[] args) {
        createTables();
        sessionMenu();
    }

    private static void movieMenu() {
        MovieView view = new MovieView();
        view.menu();
    }

    private static void sessionMenu() {
        SessionView view = new SessionView();
        view.menu();
    }
}
