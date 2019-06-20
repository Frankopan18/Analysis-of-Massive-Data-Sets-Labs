

import java.io.File;
import java.util.List;
import java.util.Map;

public interface EntryTokenDAO {

	EntryToken readFromFile(File inputFile);

	SpecialPair SimHash(String text, int N);

	SpecialPair SimHash1(String text, int N);

	int identifySimiliarTexts(EntryToken token, int indexOfQuery, List<SpecialPair> hashes);

	Map<Integer, List<Integer>> localitySensitiveHashing(EntryToken token, List<String> hashes);

	int LSHidentifySimiliarTexts(Map<Integer, List<Integer>> candidates, EntryToken token, int indexOfQuery,
			List<SpecialPair> hashes);

}