import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * PetDatabase class serves as the main entry point for the application.
 *
 * @author James West
 * @version 1.0
 */
public class PetDatabase {
	/**
	 * The main method which serves as the entry point for the application.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.printf("%s\n", "Welcome to Pet Database.");

		PetRegistry pets = new PetRegistry(new ArrayList<>(Arrays.asList(
			new Pet("Kitty", 8),
			new Pet("Bruno", 7),
			new Pet("Boomer", 8),
			new Pet("Boomer", 3),
			new Pet("Fiesty", 3)
		)));
		
		PetDatabaseMenu petDatabaseMenu = new PetDatabaseMenu(pets, scanner);
		petDatabaseMenu.run();
		scanner.close();
	}
}