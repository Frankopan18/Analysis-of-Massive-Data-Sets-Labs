import java.io.File;
import java.util.List;

public interface EntryTokenDAO {

	EntryToken readFromFile(File inputFile);

	List<Double> collaborativeFiltering(EntryToken token);

}