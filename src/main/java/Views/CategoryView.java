package Views;

import Controllers.CategoryController;
import Models.Book;
import Models.Category;

import java.util.Scanner;

public class CategoryView {
    CategoryController controller = new CategoryController();

    public CategoryView() {
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- MENU GENEROS ---");
            System.out.println("1 - CRIAR GENERO");
            System.out.println("2 - EDITAR GENERO");
            System.out.println("3 - DELETAR GENERO");
            System.out.println("4 - LISTAR GENERO");
            System.out.println("5 - LISTAR LIVROS");
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
        Category category = new Category();

        System.out.println("--- CRIAR GENERO ---");

        System.out.println("Name: ");
        category.setName(scanner.nextLine());

        controller.createCategory(category);
        System.out.println("Done!");
    }

    private void menuEdit() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--- EDITAR GENERO ---");
            printCategoryList();

            Category category = getById();
            if (category == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            edit(category);
            return;
        }
    }

    private void edit(Category category) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--- DADOS ---");
            System.out.printf("1 - Name: %s\n", category.getName());
            System.out.println("0 - CONFIRMAR");
            System.out.println("-1 - CANCELAR");

            System.out.println("Escolha uma opcao para mudar:");
            int choice = ViewUtils.getChoice(scanner);

            switch (choice) {
                case -1:
                    return;
                case 0:
                    controller.editCategory(category);
                    return;
                case 1:
                    System.out.println("Escreva o novo nome:");
                    category.setName(scanner.nextLine());
                    break;
                default:
                    System.out.println("Escolha invalida!");
                    break;
            }
        }
    }

    private void menuDelete() {
        System.out.println("--- DELETAR GENERO ---");
        printCategoryList();

        Category category = getById();
        if (category == null) {
            System.out.println("ID Invalido, tente novamente.");
            return;
        }

        controller.deleteCategory(category);
        System.out.println("Feito!");
    }

    private void menuList() {
        System.out.println("--- GENEROS ---");
        printCategoryList();
        System.out.println();
    }

    private void menuBookList() {
        while (true) {
            System.out.println("--- LIVROS DO GENERO ---");
            printCategoryList();

            System.out.println("Escolha o ID do Genero que quer mostrar os livros:");
            Category category = getById();
            if (category == null) {
                System.out.println("ID Invalido, tente novamente.");
                continue;
            }

            printBookList(category);
            System.out.println();
            return;
        }
    }

    public void printCategoryList() {
        for (Category category : controller.listCategories()) {
            System.out.println(category);
        }
    }

    public void printBookList(Category category) {
        for (Book book : controller.listBooksByCategory(category)) {
            System.out.println(book);
        }
    }

    private Category getById() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha ID:");
        Long chosen_id = (long) ViewUtils.getChoice(scanner);

        return controller.getById(chosen_id);
    }
}
