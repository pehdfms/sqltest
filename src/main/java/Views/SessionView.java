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
                    //menuCreate();
                    break;
                case 2:
                    //menuEdit();
                    break;
                case 3:
                    //menuDelete();
                    break;
                case 4:
                    //menuList();
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

        System.out.println("--- CREATE SESSION ---");

        while (true) {
            System.out.println("Movie playing (0 for none): ");
            Long choice = Long.valueOf(ViewUtils.getChoice(scanner));
            if (choice == 0) {
                session.setPlaying(null);
                break;
            } else {
                MovieController movieController = new MovieController();
                Movie movie = movieController.getById(choice);
                if (movie == null) {
                    System.out.println("Invalid ID, try again.");
                    continue;
                }

                session.setPlaying(movie);
            }
        }

        System.out.println("Date: ");
        session.setStartDate(scanner.nextLine());

        //controller.createSession(session);
        System.out.println("Done!");
    }
}
