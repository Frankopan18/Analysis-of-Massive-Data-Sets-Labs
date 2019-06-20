
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EntryTokenDAOImpl implements EntryTokenDAO {

	public EntryToken readFromFile(File inputFile) {
		EntryToken entryToken = new EntryToken();
		Scanner sc = null;
		String line = null;
		int n = 0;
		int Q = 0;
		double beta = .0;
		int queryCounter = 0;
		List<Pair> sparseMatrix = new LinkedList<>();
		Map<Integer, QueryPair> queries = new LinkedHashMap<>();
		int counter = 0;
		int i = 0;
		try {
			sc = new Scanner(System.in);
			while (sc.hasNextLine()) {
				line = sc.nextLine();

				if (counter == 0) {
					String[] parts = line.split(" ");
					n = Integer.parseInt(parts[0]);
					beta = Double.parseDouble(parts[1]);
					counter++;
					continue;
				} else if (counter < n + 1) {
					counter++;
					Pair p = new Pair();
					p.setEntryNodeIndex(i++);
					List<Integer> exitNodeIndexes = new LinkedList<>();
					String[] rowParts = line.split(" ");
					for (String part : rowParts) {
						exitNodeIndexes.add(Integer.parseInt(part));
					}
					p.setExitNodeIndexes(exitNodeIndexes);
					sparseMatrix.add(p);
					continue;

				} else if (counter == n + 1) {
					Q = Integer.parseInt(line);
					counter++;
					continue;
				} else {
					String[] parts = line.split(" ");
					// key is node
					int key = Integer.parseInt(parts[0]);
					// value is time
					int time = Integer.parseInt(parts[1]);
					QueryPair p = new QueryPair();
					p.setKey(key);
					p.setVal(time);
					queries.put(queryCounter++, p);
				}
			}
		} finally {
			sc.close();
		}
		entryToken.setBeta(beta);
		entryToken.setN(n);
		entryToken.setQ(Q);
		entryToken.setSparseMatrix(sparseMatrix);
		entryToken.setQueries(queries);
		return entryToken;
	}

	@Override
	public void executeNodeRank(EntryToken token) {
		Map<Integer, QueryPair> queries = token.getQueries();
		List<Pair> sparseMatrix = token.getSparseMatrix();
		int N = token.getN();
		double beta = token.getBeta();
		double[] r_t = new double[N];
		List<double[]> nodePerIterations = new LinkedList<>();
		
		for (int i = 0; i < N; i++) {
			r_t[i] = 1.0 / N;
		}
		nodePerIterations.add(r_t);
		
		for (int t = 1; t <= 100; t++) {
			double[] r_t1 = new double[N];

			for (int i = 0; i < N; i++) {
				r_t1[i] = (1.0 - beta) / N;
			}
			
			for (Pair node : sparseMatrix) {
				int degree = node.getExitNodeIndexes().size();
				for (int j = 0; j < degree; j++) {
					r_t1[node.getExitNodeIndexes().get(j)] += beta * nodePerIterations.get(t-1)[node.getEntryNodeIndex()]/ degree;
				}
			}
			nodePerIterations.add(r_t1);
		}

		for (Map.Entry<Integer, QueryPair> query : queries.entrySet()) {
			int time = query.getValue().getVal();
			int node = query.getValue().getKey();
			DecimalFormat formatter = new DecimalFormat("#0.0000000000");
			// r(t) i r(t+1) nisu ono sto mislis...provjeri to jos...
			// provjeri zasto ne radi za node = 0,za bilo koji t preko 1 je isti...pa gledaj sta ne valja gdje se to mijenja
			BigDecimal bd = new BigDecimal(nodePerIterations.get(time)[node]);
			System.out.println(formatter.format(bd).replace(",", "."));
		}
	}
}
