package Views;

import Controllers.MovieController;
import Models.Movie;

import java.util.Scanner;

public class MovieView {
    MovieController controller = new MovieController();

    public MovieView() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- MOVIE MENU ---");
            System.out.println("1 - CREATE MOVIE");
            System.out.println("2 - EDIT MOVIE");
            System.out.println("3 - DELETE MOVIE");
            System.out.println("4 - LIST MOVIES");
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
        Movie movie = new Movie();

        System.out.println("--- CREATE MOVIE ---");

        System.out.println("Name: ");
        movie.setName(scanner.nextLine());

        System.out.println("Category: ");
        movie.setCategory(scanner.nextLine());

        System.out.println("Duration: ");
        movie.setDuration(scanner.nextLine());

        controller.createMovie(movie);
        System.out.println("Done!");
    }

    private void menuEdit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--- EDIT MOVIE ---");
            printMovieList();

            Movie movie = getById();
            if (movie == null) {
                System.out.println("Invalid ID, try again.");
                continue;
            }

            edit(movie);
            return;
        }
    }

    private void edit(Movie movie) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- DATA ---");
            System.out.printf("1 - Name: %s\n", movie.getName());
            System.out.printf("2 - Category: %s\n", movie.getCategory());
            System.out.printf("3 - Duration: %s\n", movie.getDuration());
            System.out.println("0 - CONFIRM");
            System.out.println("-1 - CANCEL");

            System.out.println("Choose one option to change:");
            int choice = ViewUtils.getChoice(scanner);

            switch (choice) {
                case -1:
                    return;
                case 0:
                    controller.editMovie(movie);
                    return;
                case 1:
                    System.out.println("Input new Name:");
                    movie.setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Input new Category:");
                    movie.setCategory(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Input new Duration");
                    movie.setDuration(scanner.nextLine());
                    break;
                default:
                    System.out.println("Invalid option!");
                    break;
            }
        }
    }

    private void menuDelete() {
        System.out.println("--- DELETE MOVIE ---");
        printMovieList();

        Movie movie = getById();
        if (movie == null) {
            System.out.println("Invalid ID, try again.");
            return;
        }

        controller.deleteSession(movie);
        System.out.println("Done!");
    }

    private void menuList() {
        System.out.println("--- MOVIES ---");
        printMovieList();
        System.out.println();
    }

    public void printMovieList() {
        for (Movie movie : controller.listMovies()) {
            System.out.println(movie);
        }
    }

    private Movie getById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose ID:");
        Long chosen_id = (long) ViewUtils.getChoice(scanner);

        return controller.getById(chosen_id);
    }
}
