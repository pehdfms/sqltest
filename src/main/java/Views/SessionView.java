package Views;

import Controllers.MovieController;
import Controllers.SessionController;
import Models.Movie;
import Models.Session;

import java.util.Scanner;

public class SessionView {
    SessionController controller = new SessionController();

    public SessionView() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- SESSION MENU ---");
            System.out.println("1 - CREATE SESSION");
            System.out.println("2 - EDIT SESSION");
            System.out.println("3 - DELETE SESSION");
            System.out.println("4 - LIST SESSIONS");
            System.out.println("0 - EXIT");
            System.out.println();

            int choice = ViewUtils.getChoice(scanner);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    menuCreate();
                    break;
                case 2:
                    menuEdit();
                    break;
                case 3:
                    menuDelete();
                    break;
                case 4:
                    menuList();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    private void menuCreate() {
        Scanner scanner = new Scanner(System.in);
        Session session = new Session();

        MovieView movieView = new MovieView();
        MovieController movieController = new MovieController();

        System.out.println("--- CREATE SESSION ---");

        while (true) {
            System.out.println("--- CHOOSE MOVIE ---");
            movieView.printMovieList();
            System.out.println("Movie playing (0 for none): ");
            Long choice = (long) ViewUtils.getChoice(scanner);
            if (choice == 0) {
                session.setPlaying(null);
                break;
            } else {
                Movie movie = movieController.getById(choice);
                if (movie == null) {
                    System.out.println("Invalid ID, try again.");
                    continue;
                }

                session.setPlaying(movie);
                break;
            }
        }

        System.out.println("Date: ");
        session.setStartDate(scanner.nextLine());

        controller.createSession(session);
        System.out.println("Done!");
    }

    private void menuEdit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--- EDIT SESSION ---");
            printSessionList();

            Session session = getById();
            if (session == null) {
                System.out.println("Invalid ID, try again.");
                continue;
            }

            edit(session);
            return;
        }
    }

    private void edit(Session session) {
        MovieView movieView = new MovieView();
        MovieController movieController = new MovieController();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- DATA ---");
            System.out.printf("1 - Date: %s\n", session.getStartDate());
            System.out.printf("2 - Playing: %s\n", session.getPlaying());
            System.out.println("0 - CONFIRM");
            System.out.println("-1 - CANCEL");

            System.out.println("Choose one option to change:");
            int choice = ViewUtils.getChoice(scanner);

            switch (choice) {
                case -1:
                    return;
                case 0:
                    controller.editSession(session);
                    return;
                case 1:
                    System.out.println("Input new Date:");
                    session.setStartDate(scanner.nextLine());
                    break;
                case 2:
                    while (true) {
                        System.out.println("--- CHOOSE MOVIE ---");
                        movieView.printMovieList();
                        System.out.println("Movie playing (0 for none): ");
                        Long id = (long) ViewUtils.getChoice(scanner);
                        if (id == 0) {
                            session.setPlaying(null);
                            break;
                        } else {
                            Movie movie = movieController.getById(id);
                            if (movie == null) {
                                System.out.println("Invalid ID, try again.");
                                continue;
                            }

                            session.setPlaying(movie);
                            break;
                        }
                    }
                    break;
            }
        }
    }

    private void menuDelete() {
        System.out.println("--- DELETE SESSION ---");
        printSessionList();

        Session session = getById();
        if (session == null) {
            System.out.println("Invalid ID, try again.");
            return;
        }

        controller.deleteSession(session);
        System.out.println("Done!");
    }

    private void menuList() {
        System.out.println("--- SESSIONS ---");
        printSessionList();
        System.out.println();
    }

    private void printSessionList() {
        for (Session session : controller.listSessions()) {
            System.out.println(session);
        }
    }

    private Session getById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose ID:");
        Long chosen_id = (long) ViewUtils.getChoice(scanner);

        return controller.getById(chosen_id);
    }
}
