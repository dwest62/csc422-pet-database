import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 *
 */
public class PetMenuHandler {
	private final CLIMenu menu = new CLIMenu.Builder()
         .welcome("\nWhat would you like to do?")
         .addMenuItem( "View all pets", this::viewPets)
         .addMenuItem("Add more pets", this::addPets)
         .addMenuItem("Search pets by name", this::searchPetsByName)
         .addMenuItem("Search pets by age", this::searchPetsByAge)
         .delimiter(")")
         .prompt("Your choice: ")
         .menuItemFormatter(
             (index, delimiter, description) -> String.format("%d%s %s\n", index, delimiter, description))
         .build();
	private final AutoSizeTablePrinter<Pet, PetRowMapper> tablePrinter = new AutoSizeTablePrinter<Pet, PetRowMapper>();
	private final Scanner scanner;

	private final IndexedTable<Pet, PetRowMapper> table;
	
	public PetMenuHandler(PetRegistry registry, Scanner scanner) {
		this.scanner = scanner;
		this.table = (IndexedTable<Pet, PetRowMapper>) new IndexedTable.IndexedBuilder<Pet, PetRowMapper>(
			new PetRowMapper(), new Table.Column("ID", 3, Table.Column.Alignment.RIGHT)
		)
			.addColumn(new Table.Column("NAME", 10, Table.Column.Alignment.LEFT))
			.addColumn(new Table.Column("AGE", 3, Table.Column.Alignment.RIGHT))
            .addEntries(registry.getPets())
			.build();
	}
	
	public void run() {
		this.menu.runMenu(scanner);
	}
	
	private void viewPets(Predicate<Pet> filter) {
		System.out.println(tablePrinter.process(table, filter));
	}
	
	private void viewPets() {
		System.out.println(tablePrinter.process(table));
	}
	
	private void addPets() {

		Pet newPet =  InputHelper.requestValidInput(
			scanner,
			"add pet (name, age): ",
			(input) -> System.out.println("Error parsing pet: " + input),
			new TryParse<>((str) -> {
				String[] data = str.split(" ");
				return new Pet(data[0], Integer.parseInt(data[1]));
			})
		);
	}
	
	private void searchPetsByName() {
		System.out.print("Enter a name to search: ");
		String input = scanner.nextLine();
		viewPets(pet -> Objects.equals(pet.getName(), input));
	}
	
	private void searchPetsByAge() {
		Integer age = InputHelper.requestValidInput(
			scanner,
			"Enter age to search: ",
			input -> System.out.println("Could not parse input to integer. Please try again."),
			TryParse.forInteger(),
			new Rule<Integer>(integer -> integer >= 0, (input)->System.out.println("Please enter an age above 0.")));
		viewPets(pet -> pet.getAge() == age);
	}
	
}
