
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EntryTokenDAOImpl implements EntryTokenDAO {

	// kasnije samo dodaj N,s,b,prve 3 linije i onda ih izbrisi iz basketa
	public EntryToken readFromFile(File inputFile) {
		EntryToken entryToken = new EntryToken();
		Scanner sc = null;
		String line = null;
		int N = 0;
		double s = 0.0;
		int b = 0;
		int counter = 0;
		List<Basket> baskets = new LinkedList<>();
		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				line = sc.nextLine();

				if (counter == 0) {
					N = Integer.parseInt(line);
					counter++;
					continue;
				} else if (counter == 1) {
					s = Double.parseDouble(line);
					counter++;
					continue;
				} else if (counter == 2) {
					b = Integer.parseInt(line);
					counter++;
					continue;
				}
				String[] parts = line.split(" ");
				Basket singleBasket = new Basket();
				singleBasket.setItems(Arrays.asList(parts));
				baskets.add(singleBasket);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			sc.close();
		}
		entryToken.setN(N);
		entryToken.setB(b);
		entryToken.setS(s);
		entryToken.setBaskets(baskets);
		return entryToken;
	}

	@Override
	public Map<SinglePair, Integer> executePCY(EntryToken token) {
		int numberOfBaskets = token.getN();
		int numOfCompartments = token.getB();
		double s = token.getS();
		int threshhold = (int) (s * Math.floor(numberOfBaskets));

		// first pass
		Map<Integer, Integer> numOfItems = new LinkedHashMap<>();
		List<Basket> baskets = token.getBaskets();
		for (Basket basket : baskets) {
			for (String item : basket.getItems()) {
				int index = Integer.parseInt(item);
				if (numOfItems.get(index) == null) {
					numOfItems.put(index, 0);
				}
				numOfItems.put(index, numOfItems.get(index) + 1);
			}
		}
		int someA = checkSmth(numOfItems,threshhold);
		int A = someA * (someA - 1);
		A /= 2;
		System.out.println(A);
		// second pass
		int[] compartments = new int[numOfCompartments];
		for (Basket basket : baskets) {
			for (int i = 0; i < basket.getItems().size() - 1; i++) {
				for (int j = i + 1; j < basket.getItems().size(); j++) {
					int firstItem = Integer.parseInt(basket.getItems().get(i));
					int secondItem = Integer.parseInt(basket.getItems().get(j));
					if (numOfItems.get(firstItem) >= threshhold && numOfItems.get(secondItem) >= threshhold) {
						int k = ((firstItem * numOfItems.size()) + secondItem) % numOfCompartments;
						compartments[k]++;
					}
				}
			}
		}

		// third pass
		Map<SinglePair, Integer> totalPairs = new LinkedHashMap<>();
		for (Basket basket : baskets) {
			for (int i = 0; i < basket.getItems().size() - 1; i++) {
				for (int j = i + 1; j < basket.getItems().size(); j++) {
					int firstItem = Integer.parseInt(basket.getItems().get(i));
					int secondItem = Integer.parseInt(basket.getItems().get(j));
					if (numOfItems.get(firstItem) >= threshhold && numOfItems.get(secondItem) >= threshhold) {
						int k = ((firstItem * numOfItems.size()) + secondItem) % numOfCompartments;
						if (compartments[k] >= threshhold) {
							SinglePair pair = new SinglePair(firstItem, secondItem);
							if (totalPairs.get(pair) == null) {
								totalPairs.put(pair, 0);
							}
							totalPairs.put(pair, totalPairs.get(pair) + 1);
						}
					}
				}
			}
		}
		System.out.println(totalPairs.size());
		return totalPairs;
	}

	private int checkSmth(Map<Integer, Integer> numOfItems,int threshold) {
		int count = 0;
		for(Integer pair : numOfItems.keySet()) {
			if (numOfItems.get(pair) < threshold) {
				continue;
			}
			count++;
		}
		return count;
	}
}
