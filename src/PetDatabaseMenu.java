import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * The PetDatabaseMenu class handles running the cli menu for pet database operations.
 *
 * This class provides a cli menu allowing the user to select from the following operations:
 * <ol>
 * <li>{@link #viewPets()} - View all pets</li>
 * <li>{@link #addPets()} - Add more pets</li>
 * </ol>
 *
 * @author James West
 * @version 1.0
 */
public class PetDatabaseMenu {
	// Initializes the cli menu using the builder strategy.
	private final CLIMenu menu = new CLIMenu.Builder()
         .welcome("\nWhat would you like to do?")
         .addMenuItem( "View all pets", this::viewPets)
         .addMenuItem("Add more pets", this::addPets)
//         .addMenuItem("Search pets by name", this::searchPetsByName) // To be released.
//         .addMenuItem("Search pets by age", this::searchPetsByAge)
         .delimiter(")")
         .prompt("Your choice: ")
         .menuItemFormatter(
             (index, delimiter, description) -> String.format("%d%s %s\n", index, delimiter, description))
         .build();
	// Initializes an AutoSizeTablePrinter of type <Pet, PetRowMapper>.
	private final AutoSizeTablePrinter<Pet, PetRowMapper> tablePrinter = new AutoSizeTablePrinter<Pet, PetRowMapper>();
	// Scanner used for cli input.
	private final Scanner scanner;
	// Indexed pet database table.
	private final IndexedTable<Pet, PetRowMapper> table;
	// Manages pets in database.
	private final PetRegistry registry;

	/**
	 * Constructs a Pet Menu Handler from the provided registry and scanner.
	 *
	 * @param registry The registry of pets.
	 * @param scanner The scanner used for CLI input.
	 */
	public PetDatabaseMenu(PetRegistry registry, Scanner scanner) {
		this.scanner = scanner;
		this.table = (IndexedTable<Pet, PetRowMapper>) new IndexedTable.IndexedBuilder<Pet, PetRowMapper>(
			new PetRowMapper(),
			new Table.Column("ID", 3, Table.Column.Alignment.RIGHT),
			registry.getPets()
		)
			.addColumn(new Table.Column("NAME", 10, Table.Column.Alignment.LEFT))
			.addColumn(new Table.Column("AGE", 3, Table.Column.Alignment.RIGHT))
			.build();
		this.registry = registry;
	}

	/**
	 * Runs the PetDatabaseMenu.
	 */
	public void run() {
		this.menu.runMenu(scanner);
	}

	/**
	 * Displays a table of filtered pet entries in the database.
	 *
	 * @param filter The filter applied to the table.
	 */
	private void viewPets(Predicate<Pet> filter) {
		System.out.println(tablePrinter.process(table, filter));
	}

	/**
	 * Displays a table of pet entries in the database.
	 */
	private void viewPets() {
		System.out.println(tablePrinter.process(table));
	}

	/**
	 * Prompts user for pets to add to database then adds pets.
	 */
	private void addPets() {
		System.out.println(
				"Let's add some pets to the database! Please provide each pet's name and age, separated by a space. " +
				"For example, 'Rover 5'. Enter 'done' when finished."
		);

		InputErrorHandler inputErrorHandler = (input) ->
				System.out.println(
						"Oops! There was an error parsing your input: " + input + " . Please make sure to format your" +
						" input as 'name age'. Let's try again."
			);
		List<Pet> newPets = InputHelper.requestValidInputs(
			scanner,
			"add pet (name, age): ",
				inputErrorHandler,
			new TryParse<>((str) -> {
				String[] data = str.split(" ");
				return new Pet(data[0], Integer.parseInt(data[1]));
			}),
			"done"::equalsIgnoreCase
		);
		newPets.forEach(registry::addPet);

		// Assumes addPet is successful.
		System.out.println(newPets.size() + " pets added.");
	}

	// To be released
	
//	/**
//	 * Prompts the user to enter a pet's name, searches the database for matching entries, then displays a table of any
//	 * entries matched.
//	 *
//	 * The matching is case-insensitive.
//	 */
//	private void searchPetsByName() {
//		System.out.print("Enter a name to search: ");
//		String input = scanner.nextLine();
//		viewPets(pet -> input.equalsIgnoreCase(pet.getName()));
//	}
//
//	/**
//	 * Prompts the user to enter a pet's age, searches the database for matching entries, then displays a table of any
//	 * entries matched.
//	 *
//	 * The matching is case-insensitive.
//	 */
//	private void searchPetsByAge() {
//		Integer age = InputHelper.requestValidInput(
//			scanner,
//			"Enter age to search: ",
//			input -> System.out.println("Could not parse input to integer. Please try again."),
//			TryParse.forInteger(),
//			new Rule<Integer>(integer -> integer >= 0, (input)->System.out.println("Please enter an age above 0.")));
//		viewPets(pet -> pet.getAge() == age);
//	}
	
}
