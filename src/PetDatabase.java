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
	private static final int MAX_DATABASE_SIZE = 5;
	/**
	 * The main method which serves as the entry point for the application.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {

		System.out.printf("%s\n\n", "Welcome to Pet Database.");

		Scanner scanner = new Scanner(System.in);
		
		PetRegistry pets = new PetRegistry(new ArrayList<>(Arrays.asList(
			new Pet("Kitty", 8),
			new Pet("Bruno", 7),
			new Pet("Boomer", 8),
			new Pet("Boomer", 3),
			new Pet("Fiesty", 3)
		)));
		
		pets.setMaxSize(MAX_DATABASE_SIZE);
		
		PetDatabaseMenu petDatabaseMenu = new PetDatabaseMenu(pets, scanner);
		petDatabaseMenu.run();
		scanner.close();
	}
}