import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.ResourceBundle;

/**
 * The PetDatabaseMenu class handles running the cli menu for pet database operations.
 * <p>
 * This class provides a cli menu allowing the user to select from the following operations:
 * <ol>
 * <li>{@link #viewPets()} - View all pets</li>
 * <li>{@link #addPets()} - Add more pets</li>
 * <li>{@link #updatePet()} - Updates pet data</li>
 * <li>{@link #removePet()} - Removes pet from database </li>
 * <li>{@link #searchPetsByName()} - Search pets by name</li>
 * <li>{@link #searchPetsByAge()} - Search pets by age</li>

 * </ol>
 *
 * @author James West
 * @version 1.0
 */
public class PetDatabaseMenu {
	private static final ResourceBundle messages = ResourceBundle.getBundle("messages");

	// Initializes the cli menu using the builder strategy.
	private final CLIMenu menu = new CLIMenu.Builder()
		.welcome(messages.getString("menu.welcome"))
		.addMenuItem(messages.getString("menu.viewPets"), this::viewPets)
		.addMenuItem(messages.getString("menu.addPets"), this::addPets)
		.addMenuItem(messages.getString("menu.updatePet"), this::updatePet)
		.addMenuItem(messages.getString("menu.removePet"), this::removePet)
		.addMenuItem(messages.getString("menu.searchByName"), this::searchPetsByName)
		.addMenuItem(messages.getString("menu.searchByAge"), this::searchPetsByAge)
		.delimiter(messages.getString("menu.delimiter"))
		.prompt(String.format("%s ", messages.getString("menu.prompt")))
		.menuItemFormatter(
			(index, delimiter, description) -> String.format("%d%s %s\n", index, delimiter, description))
		.build();

	// Initialize an AutoSizeTablePrinter of type <Pet, PetRowMapper>.
	private static final AutoSizeTablePrinter<Pet, PetRowMapper> tablePrinter =
		new AutoSizeTablePrinter<Pet, PetRowMapper>();


	// Manages pets in database.
	private final PetRegistry registry;
	// Scanner used for cli input.
	private final Scanner scanner;
	// Indexed pet database table.
	private final IndexedTable<Pet, PetRowMapper> table;

	/**
	 * Constructs a Pet Menu Handler from the provided registry and scanner.
	 *
	 * @param registry The registry of pets.
	 * @param scanner  The scanner used for CLI input.
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
		System.out.printf("%s\n\n", tablePrinter.process(table, filter));
	}

	/**
	 * Displays a table of pet entries in the database.
	 */
	private void viewPets() {
		System.out.printf("\n%s\n\n", tablePrinter.process(table));
	}

	/**
	 * Prompts user for pets to add to database then adds pets.
	 */
	private void addPets() {

		System.out.printf("\n%s\n", messages.getString("prompt.addPetsInstruction"));

		List<Pet> newPets = InputHelper.requestValidInputs(
			scanner,
			String.format("%s ", messages.getString("prompt.addPet")),
			input -> System.out.printf(String.format("%s\n", messages.getString("error.invalidPet")), input),
			new TryParsePet(),
			"done"::equalsIgnoreCase
		);

		// Assumes addPet is successful.
		System.out.printf("%d pets added.\n\n", newPets.size());

		newPets.forEach(registry::addPet);
	}

	/**
	 * Continuously prompts user for a valid index of Pet to update. Once a valid index is provided, continuously
	 * prompts user for new name and age. Once a valid name and age is given, updates pet with new name and age.
	 *
	 */
	private void updatePet() {
		int petId = promptPetId(messages.getString("prompt.updatePet"));
		Pet pet = registry.getPetById(petId);
		String oldName = pet.getName();
		int oldAge = pet.getAge();

		Pet tempPet = InputHelper.requestValidInput(
			scanner,
			String.format("%s ", messages.getString("prompt.updatedPetDetails")),
			input -> System.out.printf(String.format("%s\n", messages.getString("error.invalidPet")), input),
			new TryParsePet()
		);

		pet.setName(tempPet.getName());
		pet.setAge(tempPet.getAge());

		System.out.printf("%s %d changed to %s %d.\n\n", oldName, oldAge, pet.getName(), pet.getAge());
	}

	/**
	 * Displays the table of pets and continuously prompts the user to enter a pet's id until a valid pet id is
	 * provided then removes the pet.
	 */
	private void removePet() {
		int petId = promptPetId(messages.getString("prompt.removePet"));
		Pet pet = registry.removePetByID(petId);
		System.out.printf("%s %d is removed.\n\n", pet.getName(), pet.getAge());
	}

	/**
	 * Displays a table of pets and prompts user for a pet id using prompt provided.
	 *
	 * @param prompt The prompt
	 * @return The pet id provided by the user.
	 */
	private int promptPetId(String prompt) {
		return InputHelper.requestValidInput(
			scanner,
			String.format("\n%s\n%s ", tablePrinter.process(table), prompt),
			s -> System.out.printf(String.format("%s\n", messages.getString("error.invalidInt")), s),
			new TryParse<>(Integer::parseInt),
			new Rule<Integer>(
				registry::hasPet,
				s -> System.out.printf(String.format("%s\n", messages.getString("error.invalidPetIndex")), s)
			)
		);
	}

	/**
	 * Prompts the user to enter a pet's name, searches the database for matching entries, then displays a table of any
	 * entries matched.
	 * <p>
	 * The matching is case-insensitive.
	 */
	private void searchPetsByName() {
		System.out.print(messages.getString("prompt.searchByName"));
		String input = scanner.nextLine();
		viewPets(pet -> input.equalsIgnoreCase(pet.getName()));
	}

	/**
	 * Prompts the user to enter a pet's age, searches the database for matching entries, then displays a table of any
	 * entries matched.
	 * <p>
	 * The matching is case-insensitive.
	 */
	private void searchPetsByAge() {
		Integer age = InputHelper.requestValidInput(
			scanner,
			messages.getString("prompt.searchByAge"),
			input -> System.out.println(messages.getString("error.invalidInt")),
			TryParse.forInteger(),
			new Rule<Integer>(integer -> integer >= 0, (input) -> System.out.println("Please enter an age above 0.")));
		viewPets(pet -> pet.getAge() == age);
	}
}
