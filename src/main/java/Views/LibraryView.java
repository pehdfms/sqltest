package Views;

import Controllers.LibraryController;
import Models.Book;
import Models.Library;

import java.util.Scanner;

public class LibraryView {
    LibraryController controller = new LibraryController();

    public LibraryView() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- MENU BIBLIOTECA ---");
            System.out.println("1 - CRIAR BIBLIOTECA");
            System.out.println("2 - EDITAR BIBLIOTECA");
            System.out.println("3 - DELETAR BIBLIOTECA");
            System.out.println("4 - LISTAR BIBLIOTECAS");
            System.out.println("5 - LISTAR LIVROS DA BIBLIOTECA");
            System.out.println("0 - SAIR");
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
                case 5:
                    menuBookList();
                    break;
                default:
                    System.out.println("Escolha invalida!");
                    break;
            }
        }
    }

    private void menuCreate() {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        System.out.println("--- CRIAR BIBLIOTECA ---");
        System.out.println("Nome: ");
        library.setName(scanner.nextLine());

        controller.createLibrary(library);
        System.out.println("Feito!");
    }

    private void menuEdit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--- EDITAR BIBLIOTECA ---");
            printLibraryList();

            Library library = getById();
            if (library == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            edit(library);
            return;
        }
    }

    private void edit(Library library) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- DADOS ---");
            System.out.printf("1 - Nome: %s\n", library.getName());
            System.out.println("0 - CONFIRMAR");
            System.out.println("-1 - CANCELAR");

            System.out.println("Escolha uma opcao para mudar:");
            int choice = ViewUtils.getChoice(scanner);

            switch (choice) {
                case -1:
                    return;
                case 0:
                    controller.editLibrary(library);
                    return;
                case 1:
                    System.out.println("Escreva novo nome:");
                    library.setName(scanner.nextLine());
                    break;
            }
        }
    }

    private void menuDelete() {
        System.out.println("--- DELETAR BIBLIOTECA ---");
        printLibraryList();

        Library library = getById();
        if (library == null) {
            System.out.println("ID Invalido, tente novamente.");
            return;
        }

        controller.deleteLibrary(library);
        System.out.println("Feito!");
    }

    private void menuList() {
        System.out.println("--- BIBLIOTECAS ---");
        printLibraryList();
        System.out.println();
    }

    private void menuBookList() {
        while (true) {
            System.out.println("--- LIVROS DA BIBLIOTECA ---");
            printLibraryList();

            System.out.println("Escolha o ID da Biblioteca que quer mostrar os livros:");
            Library library = getById();
            if (library == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            printBookList(library);
            System.out.println();
            return;
        }
    }

    public void printLibraryList() {
        for (Library library : controller.listLibraries()) {
            System.out.println(library);
        }
    }

    public void printBookList(Library library) {
        for (Book book : controller.listBooksByLibrary(library)) {
            System.out.println(book);
        }
    }

    private Library getById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha ID:");
        Long chosen_id = (long) ViewUtils.getChoice(scanner);

        return controller.getById(chosen_id);
    }
}
