import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PetDatabase {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		PetRegistry pets = new PetRegistry(new ArrayList<>(Arrays.asList(
			new Pet("Kitty", 8),
			new Pet("Bruno", 7),
			new Pet("Boomer", 8),
			new Pet("Boomer", 3),
			new Pet("Fiesty", 3)
		)));
		
		PetMenuHandler menu = new PetMenuHandler(pets, scanner);
		menu.run();
		scanner.close();
	}
}