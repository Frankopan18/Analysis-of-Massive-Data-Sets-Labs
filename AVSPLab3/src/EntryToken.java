import java.util.List;

public class EntryToken {

	private int N; // number of lines
	private int M; // number of values in each line
	private List<String> matrixLines;
	private int Q; // number of queries
	private List<TokenQuery> queryLines;

	public EntryToken(int n, int m, List<String> matrixLines, int q, List<TokenQuery> queryLines) {
		super();
		N = n;
		M = m;
		this.matrixLines = matrixLines;
		Q = q;
		this.queryLines = queryLines;
	}

	public EntryToken() {

	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public List<String> getMatrixLines() {
		return matrixLines;
	}

	public void setMatrixLines(List<String> matrixLines) {
		this.matrixLines = matrixLines;
	}

	public int getQ() {
		return Q;
	}

	public void setQ(int q) {
		Q = q;
	}

	public List<TokenQuery> getQueryLines() {
		return queryLines;
	}

	public void setQueryLines(List<TokenQuery> queryLines) {
		this.queryLines = queryLines;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + M;
		result = prime * result + N;
		result = prime * result + Q;
		result = prime * result + ((matrixLines == null) ? 0 : matrixLines.hashCode());
		result = prime * result + ((queryLines == null) ? 0 : queryLines.hashCode());
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
		if (M != other.M)
			return false;
		if (N != other.N)
			return false;
		if (Q != other.Q)
			return false;
		if (matrixLines == null) {
			if (other.matrixLines != null)
				return false;
		} else if (!matrixLines.equals(other.matrixLines))
			return false;
		if (queryLines == null) {
			if (other.queryLines != null)
				return false;
		} else if (!queryLines.equals(other.queryLines))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntryToken [N=" + N + ", M=" + M + ", matrixLines=" + matrixLines + ", Q=" + Q + ", queryLines="
				+ queryLines + "]";
	}

}