package Views;

import Controllers.BookController;
import DAOs.CategoryDAO;
import DAOs.LibraryDAO;

import java.util.Scanner;

public class MainView {
    public MainView() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- MENU PRINCIPAL ---");
            System.out.println("1 - MENU BIBLIOTECA");
            System.out.println("2 - MENU LIVROS");
            System.out.println("3 - MENU GENEROS");
            System.out.println("0 - SAIR");
            System.out.println();

            int choice = ViewUtils.getChoice(scanner);
            switch (choice) {
                case 0:
                    return;
                case 1:
                    new LibraryView().menu();
                    break;
                case 2:
                    if (new LibraryDAO().listLibraries().size() == 0) {
                        System.out.println("Nao existem bibliotecas! Crie uma primeiro.");
                        break;
                    }
                    if (new CategoryDAO().listCategories().size() == 0) {
                        System.out.println("Nao existem generos! Crie um primeiro.");
                        break;
                    }
                    new BookView().menu();
                    break;
                case 3:
                    new CategoryView().menu();
                    break;
                default:
                    System.out.println("Escolha invalida!");
                    break;
            }
        }
    }
}
