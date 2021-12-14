package Views;

import Controllers.BookController;
import Controllers.CategoryController;
import Controllers.LibraryController;
import Models.Book;
import Models.Category;
import Models.Library;

import java.util.List;
import java.util.Scanner;

public class BookView {
    BookController controller = new BookController();

    public BookView() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- MENU LIVROS ---");
            System.out.println("1 - CRIAR LIVRO");
            System.out.println("2 - EDITAR LIVRO");
            System.out.println("3 - DELETAR LIVRO");
            System.out.println("4 - LISTAR LIVRO");
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
                default:
                    System.out.println("Escolha invalida!");
                    break;
            }
        }
    }

    private void menuCreate() {
        Scanner scanner = new Scanner(System.in);

        CategoryView categoryView = new CategoryView();
        CategoryController categoryController = new CategoryController();

        LibraryView libraryView = new LibraryView();
        LibraryController libraryController = new LibraryController();

        Book book = new Book();

        System.out.println("--- CRIAR LIVRO ---");

        System.out.println("Nome: ");
        book.setName(scanner.nextLine());

        System.out.println("Categoria: ");
        Category category;
        while (true) {
            System.out.println("--- ESCOLHA CATEGORIA ---");
            categoryView.printCategoryList();
            System.out.println("Categoria do livro:");
            Long choice = (long) ViewUtils.getChoice(scanner);
            category = categoryController.getById(choice);
            if (category == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            List<Book> categoryBooks = category.getBooks();
            categoryBooks.add(book);
            category.setBooks(categoryBooks);

            categoryController.editCategory(category);
            break;
        }

        System.out.println("Biblioteca: ");
        Library library;
        while (true) {
            System.out.println("--- ESCOLHA BIBLIOTECA ---");
            libraryView.printLibraryList();
            System.out.println("Biblioteca do livro:");
            Long choice = (long) ViewUtils.getChoice(scanner);
            library = libraryController.getById(choice);
            if (library == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            List<Book> libraryBooks = library.getBooks();
            libraryBooks.add(book);

            library.setBooks(libraryBooks);
            libraryController.editLibrary(library);
            break;
        }

        controller.createBook(book, category, library);
        System.out.println("Feito!");
    }

    private void menuEdit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--- EDITAR LIVRO ---");
            printBookList();

            Book book = getById();
            if (book == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            edit(book);
            return;
        }
    }

    private void edit(Book book) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- DADOS ---");
            System.out.printf("1 - Nome: %s\n", book.getName());
            System.out.println("0 - CONFIRM");
            System.out.println("-1 - CANCEL");

            System.out.println("Escolha uma opcao para mudar:");
            int choice = ViewUtils.getChoice(scanner);

            switch (choice) {
                case -1:
                    return;
                case 0:
                    controller.editBook(book, controller.getCategory(book), controller.getLibrary(book));
                    return;
                case 1:
                    System.out.println("Escreva novo Nome:");
                    book.setName(scanner.nextLine());
                    break;
                default:
                    System.out.println("Escolha invalida!");
                    break;
            }
        }
    }

    private void menuDelete() {
        System.out.println("--- DELETAR LIVRO ---");
        printBookList();

        Book book = getById();
        if (book == null) {
            System.out.println("ID Invalido, tente novamente.");
            return;
        }

        controller.deleteBook(book);
        System.out.println("Feito!");
    }

    private void menuList() {
        System.out.println("--- LIVROS ---");
        printBookList();
        System.out.println();
    }

    public void printBookList() {
        for (Book book : controller.listBooks()) {
            System.out.println(book);
        }
    }

    private Book getById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha ID:");
        Long chosen_id = (long) ViewUtils.getChoice(scanner);

        return controller.getById(chosen_id);
    }
}
