import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EntryToken {

	private int n;
	private double beta; // probability for teleportation is (1-beta), beta to follow the edges
	List<Pair> sparseMatrix = new LinkedList<>();
	private int Q;
	// make sure to make method getKeyForValue(get(key) --> vraca value),a sve skupa
	// vraca key
	Map<Integer, QueryPair> queries = new LinkedHashMap<>(); // key is the node, value is the time,nodes will be
															// different,time can be same

	public EntryToken(int n, double beta, List<Pair> sparseMatrix, int q, Map<Integer, QueryPair> queries) {
		super();
		this.n = n;
		this.beta = beta;
		this.sparseMatrix = sparseMatrix;
		Q = q;
		this.queries = queries;
	}

	public EntryToken() {
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public List<Pair> getSparseMatrix() {
		return sparseMatrix;
	}

	public void setSparseMatrix(List<Pair> sparseMatrix) {
		this.sparseMatrix = sparseMatrix;
	}

	public int getQ() {
		return Q;
	}

	public void setQ(int q) {
		Q = q;
	}

	public Map<Integer, QueryPair> getQueries() {
		return queries;
	}

	public void setQueries(Map<Integer, QueryPair> queries) {
		this.queries = queries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Q;
		long temp;
		temp = Double.doubleToLongBits(beta);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + n;
		result = prime * result + ((queries == null) ? 0 : queries.hashCode());
		result = prime * result + ((sparseMatrix == null) ? 0 : sparseMatrix.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntryToken other = (EntryToken) obj;
		if (Q != other.Q)
			return false;
		if (Double.doubleToLongBits(beta) != Double.doubleToLongBits(other.beta))
			return false;
		if (n != other.n)
			return false;
		if (queries == null) {
			if (other.queries != null)
				return false;
		} else if (!queries.equals(other.queries))
			return false;
		if (sparseMatrix == null) {
			if (other.sparseMatrix != null)
				return false;
		} else if (!sparseMatrix.equals(other.sparseMatrix))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntryToken [n=" + n + ", beta=" + beta + ", sparseMatrix=" + sparseMatrix + ", Q=" + Q + ", queries="
				+ queries + "]";
	}

}