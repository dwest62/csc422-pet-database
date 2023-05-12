import java.util.List;

/**
 * The class PetRowMapper implements IndexedRowMapper enabling a pet to be mapped to a row represented by a list of
 * strings storing the pet's name and age.
 *
 *
 * @author James West
 * @version 1.0
 */
class PetRowMapper implements IndexedRowMapper<Pet> {
	/**
	 * Maps a Pet to a row represented by a list of strings containing the pet's name and string representation of the
	 * pet's age.
	 *
	 *
	 * @param pet The pet to be mapped.
	 * @return A pet row entry represented by list of strings containing the pet's name and age.
	 */
	@Override
	public List<String> mapRow(Pet pet) {
		return List.of(pet.getName(), String.valueOf(pet.getAge()));
	}
	/**
	 * Maps a Pet with its  to a row represented by a list of strings containing the pet's name and string representation of the
	 * pet's age.
	 *
	 * This method is useful when mapping needs to account for the position of the pet in its data source.
	 *
	 * @param pet 		The pet to be mapped.
	 * @param index 	The index of the pet in its data source.
	 * @return A pet row entry represented by list of strings containing, the index of the pet in its data source, the
	 * 		   pet's name and age.
	 */
	@Override
	public List<String> mapRow(Pet pet, int index) {
		return List.of(String.valueOf(index), pet.getName(), String.valueOf(pet.getAge()));
	}
}
