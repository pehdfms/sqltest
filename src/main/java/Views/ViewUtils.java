package Views;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ViewUtils {
    public static int getChoice(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            return -1;
        } finally {
            scanner.nextLine();
        }
    }
}
