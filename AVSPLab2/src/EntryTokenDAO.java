import java.io.File;
import java.util.Map;

public interface EntryTokenDAO {

	EntryToken readFromFile(File inputFile);

	Map<SinglePair, Integer> executePCY(EntryToken token);

}