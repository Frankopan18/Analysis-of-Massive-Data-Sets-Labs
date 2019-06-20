

import java.util.List;

public class EntryToken {

	private int N;

	private List<String> texts;

	private int Q;

	private List<SinglePair> queries;

	public EntryToken(int n, List<String> texts, int q, List<SinglePair> queries) {
		super();
		N = n;
		this.texts = texts;
		Q = q;
		this.queries = queries;
	}

	public EntryToken() {
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public List<String> getTexts() {
		return texts;
	}

	public void setTexts(List<String> texts) {
		this.texts = texts;
	}

	public int getQ() {
		return Q;
	}

	public void setQ(int q) {
		Q = q;
	}

	public List<SinglePair> getQueries() {
		return queries;
	}

	public void setQueries(List<SinglePair> queries) {
		this.queries = queries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + N;
		result = prime * result + Q;
		result = prime * result + ((queries == null) ? 0 : queries.hashCode());
		result = prime * result + ((texts == null) ? 0 : texts.hashCode());
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
		if (N != other.N)
			return false;
		if (Q != other.Q)
			return false;
		if (queries == null) {
			if (other.queries != null)
				return false;
		} else if (!queries.equals(other.queries))
			return false;
		if (texts == null) {
			if (other.texts != null)
				return false;
		} else if (!texts.equals(other.texts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntryToken [N=" + N + ", texts=" + texts + ", Q=" + Q + ", queries=" + queries + "]";
	}

}
