import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * PetDatabase class serves as the main entry point for the application.
 *
 * @author James West
 * @version 1.1
 */
public class PetDatabase {
	
	/**
	 * The main method which serves as the entry point for the application.
	 *
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		
		String file = "pets.dat";
		if(args.length == 1)
			file = args[0];

		System.out.printf("%s\n\n", "Welcome to Pet Database.");
		
		try (Scanner scanner = new Scanner(System.in)) {
			PetRegistry pets = PetRegistry.load(file);
			PetDatabaseMenu petDatabaseMenu = new PetDatabaseMenu(pets, scanner);
			petDatabaseMenu.run();
			pets.save(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
	}
}