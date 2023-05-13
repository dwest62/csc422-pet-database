import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The PetRegistry class provides methods for managing a collection of Pets. It supports adding, sorting, and viewing pets.
 *
 * @author James West
 * @version 1.0
 */
public class PetRegistry {
	private final ArrayList<Pet> pets;
	
	
	public PetRegistry(ArrayList<Pet> pets) {this.pets = pets;}
	
	/**
	 * Adds a pet to the registry.
	 *
	 * @param pet   The pet to be added.
	 */
	public void addPet(Pet pet) {
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
}
