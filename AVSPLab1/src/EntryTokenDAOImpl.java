

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

public class EntryTokenDAOImpl implements EntryTokenDAO {

	public EntryToken readFromFile(File inputFile) {
		EntryToken entryToken = new EntryToken();
		Scanner sc = null;
		String line = null;
		int N = 0;
		int Q = 0;
		boolean nLinePassed = false;
		boolean qLinePassed = false;
		List<String> texts = new LinkedList<>();
		List<SinglePair> queries = new LinkedList<>();
		try {
			sc = new Scanner(System.in);
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				// check for N
				if (!nLinePassed) {
					N = Integer.parseInt(line);
					nLinePassed = true;
					continue;
				}
				// check for Q
				if (line.split(" ").length == 1) {
					if (isANumber(line)) {
						Q = Integer.parseInt(line);
						qLinePassed = true;
						continue;
					}
				}

				if (qLinePassed) {
					String[] parts = line.split(" ");
					SinglePair pair = new SinglePair();
					pair.setI(Integer.parseInt(parts[0]));
					pair.setK(Integer.parseInt(parts[1]));
					queries.add(pair);
				} else {
					texts.add(line);
				}
			}
		} finally {
			sc.close();
		}

		entryToken.setN(N);
		entryToken.setQ(Q);
		entryToken.setTexts(texts);
		entryToken.setQueries(queries);

		return entryToken;
	}

	private boolean isANumber(String line) {
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			int ascii = (int) c;
			if (ascii >= 48 && ascii <= 57) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}

	public SpecialPair SimHash1(String text, int N) {
		SpecialPair specialPair = new SpecialPair();
		if (N > 50000) {
			try {
				throw new Exception("N is too high");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int[] sh = new int[128];
		String[] parts = text.split(" ");
		for (String unit : parts) {
			byte[] hash = DigestUtils.md5(unit);
			int counter = 0;
			for (int i = 0; i < hash.length; i++) {
				String binaryRep = Integer.toBinaryString((hash[i] & 0xFF) + 0x100).substring(1);
				for (int j = 0; j < binaryRep.length(); j++) {
					if (Character.getNumericValue(binaryRep.charAt(j)) == 1) {
						sh[counter] += 1;
					} else {
						sh[counter] -= 1;
					}
					counter++;
				}
			}
		}
		for (int i = 0; i < sh.length; i++) {
			if (sh[i] >= 0) {
				sh[i] = 1;
			} else {
				sh[i] = 0;
			}
		}
		specialPair.setBinaryRepresentation(sh);
		String hexValue = getHex(sh);
		specialPair.setHexRepresentation(hexValue);
		return specialPair;
	}

	public SpecialPair SimHash(String text, int N) {
		SpecialPair specialPair = new SpecialPair();
		if (N > 1000) {
			try {
				throw new Exception("N is too high");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		int[] sh = new int[128];
		String[] parts = text.split(" ");
		for (String unit : parts) {
			// hash vrati bajtove,pa njih pretvori u bitove i promijeni tamo gdje je
			// jedinica
			byte[] hash = DigestUtils.md5(unit);
			// hash ima 16 ,sh 128,pretvori hash odmah u binary repove i onda iteriraj po
			// njima,to POPRAVI
			// uvijek u hashu ima 16 brojeva koje transformiras u bitove i onda tako gledas
			// nekad ima 7 kad se transformira
			int counter = 0;
			for (int i = 0; i < hash.length; i++) {
				// get rids of leading 1's if it's negative and has always 8 digits for a byte
				String binaryRep = Integer.toBinaryString((hash[i] & 0xFF) + 0x100).substring(1);
				for (int j = 0; j < binaryRep.length(); j++) {
					if (Character.getNumericValue(binaryRep.charAt(j)) == 1) {
						sh[counter] += 1;
					} else {
						sh[counter] -= 1;
					}
					counter++;
				}
			}
		}
		for (int i = 0; i < sh.length; i++) {
			if (sh[i] >= 0) {
				sh[i] = 1;
			} else {
				sh[i] = 0;
			}
		}
		specialPair.setBinaryRepresentation(sh);
		String hexValue = getHex(sh);
		specialPair.setHexRepresentation(hexValue);
		return specialPair;
	}

	private String getHex(int[] sh) {
		int count = 0;
		String retVal = "";
		String singleVal = "";
		for (int i = 0; i < sh.length; i++) {
			singleVal += Integer.toString(sh[i]);
			count++;
			if (count == 4) {
				count = 0;
				int singleHex = Integer.parseInt(singleVal, 2);
				String hexStr = Integer.toString(singleHex, 16);
				retVal += hexStr;
				singleVal = "";
			}
		}
		return retVal;
	}

	@Override
	public int identifySimiliarTexts(EntryToken token, int indexOfQuery, List<SpecialPair> hashes) {
		// napravi za jedan query pa kasnije spoji sve
		// 1) Ucitaj sve
		// 2) Izracunaj sve simHash sazetke
		// 3)iteriraj po svim sazecima
		// 4) ako je i!=I,provjeri na koliko se bitova razlikuju(HEX BITOVA)
		// 5) ako je taj broj strogo veci od K,onda counter povecaj
		// 6) vrati counter i ispisi ga
		int similiar = 0;
		int indexOfI = token.getQueries().get(indexOfQuery).getI();
		int K = token.getQueries().get(indexOfQuery).getK();
		for (SpecialPair hash : hashes) {
			// usporedjujes hash sa sazetkom I-tog hasha
			if (hashes.indexOf(hash) != indexOfI) {
				// gledaj bitwise,a ne string dal je isti,promijenio si ovo,sad promijeni jos
				// malo,SpecialPair umjesto hashes
				// tu nesto..
				int concurrences = similiarBits1(hash, hashes.get(indexOfI));
				if (concurrences <= K) {
					similiar++;
				}
			}
		}
		return similiar;
	}

	private int similiarBits1(SpecialPair hash, SpecialPair hashToCompare2) {
		int[] hashToCompare = hashToCompare2.getBinaryRepresentation();
		int counter = 0;
		for (int i = 0; i < hashToCompare.length; i++) {
			if (hash.getBinaryRepresentation()[i] != hashToCompare[i]) {
				counter++;
			}
		}
		return counter;
	}

	@Override
	public Map<Integer, List<Integer>> localitySensitiveHashing(EntryToken token, List<String> simHash) {
		int val = 0;
		int endPosition = 32;
		// creates candidates with empty lists(keys are indexes of texts)..
		Map<Integer, List<Integer>> candidates = new LinkedHashMap<>();
		for (int i = 0; i < token.getN(); i++) {
			candidates.put(i, new LinkedList<Integer>());
		}
		for (int belt = 1; belt <= 8; belt++) {
			Map<Integer, List<Integer>> compartments = new LinkedHashMap<>();
			for (int currentID = 0; currentID < token.getN() - 1; currentID++) {
				String hash = simHash.get(currentID);
				// take first 16 bits but from begginning which means from the end of the hash
				// substring from endPosition =
				val = Integer.parseInt("" + hash.substring(endPosition - 4, endPosition), 16);
				List<Integer> textInCompartment = new LinkedList<>();
				if (compartments.get(val) != null) {
					textInCompartment = compartments.get(val);
					for (Integer textID : textInCompartment) {
						candidates.get(currentID).add(textID);
						candidates.get(textID).add(currentID);
					}
				} else {
					textInCompartment.clear();
				}
				textInCompartment.add(currentID);
				compartments.put(val, textInCompartment);
			}
			endPosition -= 4; // - 4 hex digits which means 16 bits
		}
		return candidates;
	}

	@Override
	public int LSHidentifySimiliarTexts(Map<Integer, List<Integer>> candidates, EntryToken token, int indexOfQuery,
			List<SpecialPair> hashes) {
		int similiar = 0;
		int indexOfI = token.getQueries().get(indexOfQuery).getI();
		int K = token.getQueries().get(indexOfQuery).getK();
		List<Integer> singleCandidate = candidates.get(indexOfI);
		for (Integer indexOfText : singleCandidate) {
			SpecialPair singleHash = hashes.get(indexOfText);
			if (hashes.indexOf(singleHash) != indexOfI) {
				int concurrences = similiarBits2(singleHash, hashes.get(indexOfI), K);
				if (concurrences <= K) {
					similiar++;
				}
			}
		}
		return similiar;
	}

	private int similiarBits2(SpecialPair singleHash, SpecialPair IHash, Integer K) {
		int[] hashToCompare = singleHash.getBinaryRepresentation();
		int counter = 0;
		for (int i = 0; i < hashToCompare.length; i++) {
			if (IHash.getBinaryRepresentation()[i] != hashToCompare[i]) {
				counter++;
			}
			if (counter > K) {
				break;
			}
		}
		return counter;
	}
}
