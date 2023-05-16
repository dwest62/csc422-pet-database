import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The PetRegistry class provides methods for managing a collection of Pets. It supports adding, sorting, and viewing pets.
 *
 * @author James West
 * @version 1.1
 */
public class PetRegistry implements Serializable {
	private Integer maxSize;
	private final List<Pet> pets;

	
	
	public PetRegistry(ArrayList<Pet> pets)
	{
		this.pets = pets;
	}
	public PetRegistry()
	{
		this.pets = new ArrayList<>();
	}
	
	/**
	 * Adds a pet to the registry.
	 *
	 * @param pet   The pet to be added.
	 * @throws IllegalArgumentException If Registry is full.
	 */
	public void addPet(Pet pet) {
		if(maxSize != null && this.pets.size() == maxSize)
			throw new IllegalArgumentException("Error adding pet: Registry full.");
		this.pets.add(pet);
	}
	
	/**
	 * Returns an unmodifiable view of the list of pets.
	 *
	 * <p>This method allows for safe access to the list of pets. Any attempt to modify the list will result in an
	 * UnsupportedOperationException. To modify the list, use the PetRegistry api.</p>
	 *
	 * @return An unmodifiable view of the list of pets.
	 */
	public List<Pet> getPets() {
		return Collections.unmodifiableList(this.pets);
	}
	
	/**
	 * Returns true if pet id is in the list, otherwise false.
	 *
	 * @param id    The index of the pet representing its id.
	 * @return      True if the id is in the list, otherwise false.
	 */
	public Boolean hasPet(int id) {
		return id >= 0 && id < this.pets.size();
	}

	/**
	 * Returns pet at provided index representing the pet's id.
	 *
	 * @param id The index of the pet representing its id.
	 * @return The pet at the provided index.
	 */
	public Pet getPetById(int id) {
		return pets.get(id);
	}

	/**
	 * Removes a pet from the registry.
	 *
	 * @param id The index of the pet representing its id.
	 * @return Pet The pet removed.
	 */
	public Pet removePetByID(int id) {return pets.remove(id);}

	/**
	 * Returns a list of pets that match the provided age.
	 *
	 * <p>This method searches through the pet registry and returns a new list containing only pets that have an age
	 * matching the input age. If no pets matching the input name are found, an empty list is returned.
	 * </p>
	 *
	 * @param age   The age of the pets to retrieve.
	 * @return A new list containing any pets which match the provided age.
	 */
	public List<Pet> getPetsByAge(int age) {
		return this.pets.stream().filter(pet -> pet.getAge() == age).collect(Collectors.toList());
	}

	/**
	 * Returns a list of pets that match the provided name.
	 *
	 * <p>This method searches through the pet registry and returns a new list containing only pets that have a name
	 * matching the input name. If no pets matching the input name are found, an empty list is returned.
	 * </p>
	 *
	 *
	 * @param name  The name of the pets to retrieve.
	 * @return A new list containing any pets which match the provided name.
	 */
	public List<Pet> getPetsByName(String name) {
		return this.pets.stream().filter(pet -> Objects.equals(pet.getName(), name)).collect(Collectors.toList());
	}
	

	/**
	 * Save current registry to file.
	 *
	 * @param fileName The name of the file to save.
	 */
	public void save(String fileName) {
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
			output.writeObject(this);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Load registry from file.
	 *
	 * @param fileName The name of the file holding the pet data.
	 * @return The PetRegistry instance stored in the file, or an empty instance if file does not exist.
	 * @throws IOException If file exists, but an error occurs loading the registry from the file.
	 */
	public static PetRegistry load(String fileName) throws IOException {
		File petFile = new File(fileName);
		if (!petFile.exists()) {
			try {
				petFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Critical error creating pet database data file.");
				System.exit(0);
			}
		}
		
		try (FileInputStream fileInputStream = new FileInputStream(petFile)) {
			if (petFile.length() == 0) {
				return new PetRegistry(new ArrayList<>());
			}
			try (ObjectInputStream input = new ObjectInputStream(fileInputStream)) {
				return (PetRegistry) input.readObject();
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			}
		}
	}
	
	public void setMaxSize(int maxSize) {
		if(maxSize < pets.size())
			throw new IllegalArgumentException(String.format("Max size %d cannot be larger than current size %d",
				maxSize,
				pets.size()));
		this.maxSize = maxSize;
	}
}
