import java.util.List;

class PetRowMapper implements IndexedRowMapper<Pet> {
	@Override
	public List<String> mapRow(Pet pet) {
		throw new UnsupportedOperationException("mapRow(resultSet) is not supported in IndexedRowMapper. User" +
			                                        " mapRow(resultSet, int) instead. ");
	}
	
	@Override
	public List<String> mapRow(Pet pet, int index) {
		return List.of(String.valueOf(index), pet.getName(), String.valueOf(pet.getAge()));
	}
}
