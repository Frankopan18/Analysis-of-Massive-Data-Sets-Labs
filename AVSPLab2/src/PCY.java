import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PCY {

	public static void main(String[] args) {
		File inputFile = new File("src/R1.txt");
		EntryTokenDAO dao = new EntryTokenDAOImpl();
		EntryToken entryToken = dao.readFromFile(inputFile);
		int threshhold = (int) (entryToken.getS() * Math.floor(entryToken.getN()));
		Map<SinglePair, Integer> result = dao.executePCY(entryToken);
		Map<SinglePair, Integer> resultSortedDescending = result.entrySet()
                .stream()
                .sorted((Map.Entry.<SinglePair, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		for(SinglePair pair : resultSortedDescending.keySet()) {
			if (result.get(pair) >= threshhold) {
				System.out.println(result.get(pair));
			}
		}
	}
}
