

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class SimHash {

	public static void main(String[] args) {
		File inputFile = new File("src/main/java/R2.txt");
		EntryTokenDAO dao = new EntryTokenDAOImpl();
		EntryToken entryToken = dao.readFromFile(inputFile);
		List<SpecialPair> hashes = new LinkedList<>();
		for (int i = 0; i < entryToken.getN(); i++) {
			hashes.add(dao.SimHash(entryToken.getTexts().get(i), entryToken.getN()));
		}
		for (int i = 0; i < entryToken.getQueries().size(); i++) {
			System.out.println((dao.identifySimiliarTexts(entryToken, i, hashes)));
		}
	}
}
