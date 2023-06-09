/**
 * TryParsePet is a subclass of TryPart specialized for Pet parsing.
 *
 * @author James West
 * @version 1.1
 */
public class TryParsePet extends TryParse<Pet> {
	TryParsePet() {
		super((str) -> {
			String[] data = str.split(" ");
			if(data.length != 2) throw new IllegalArgumentException();
			return new Pet(data[0], Integer.parseInt(data[1]));
		});
	}
}
