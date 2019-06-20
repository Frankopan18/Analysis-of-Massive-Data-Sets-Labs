import java.io.File;

public class NodeRank {

	public static void main(String[] args) {
		File inputFile = new File("src/R2.txt");
		EntryTokenDAO dao = new EntryTokenDAOImpl();
		EntryToken entryToken = dao.readFromFile(inputFile);
		dao.executeNodeRank(entryToken);
	}
}
