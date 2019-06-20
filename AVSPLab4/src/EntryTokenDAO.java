import java.io.File;

public interface EntryTokenDAO {

	EntryToken readFromFile(File inputFile);

	void executeNodeRank(EntryToken token);

}