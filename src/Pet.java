/**
 * The class represents a Pet to be used to track Pet data in the Pet Database application.
 *
 * Future implementations of this class could include an id field to give Pets a static id, but for the purposes of
 * this application, the id will be tracked by a Pet's given index in a List.
 */
public class Pet {
	private String name;

	/**
	 * Constructs a pet using the provided name and age.
	 *
	 * @param name The name of the pet.
	 * @param age The age of the pet.
	 */
	public Pet(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	private int age;
	
	/**
	 * Returns the name of the Pet.
	 *
	 * @return The name of the Pet.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the Pet.
	 *
	 * @param name The new name of the Pet.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the age of the Pet.
	 *
	 * @return The age of the Pet.
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Sets the age of the Pet.
	 *
	 * Note: In future implementations, this could be tracked using a birthdate to auto-update as time passes.
	 *
	 * @param age The new age of the Pet.
	 */
	public void setAge(int age) {
		this.age = age;
	}
}
