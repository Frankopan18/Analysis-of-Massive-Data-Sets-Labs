public class QueryPair {

	private int entryNodeIndex;
	private int exitNodeIndexes;

	public QueryPair(int entryNodeIndex, int exitNodeIndexes) {
		super();
		this.entryNodeIndex = entryNodeIndex;
		this.exitNodeIndexes = exitNodeIndexes;
	}

	public QueryPair() {

	}

	public int getKey() {
		return entryNodeIndex;
	}

	public void setKey(int entryNodeIndex) {
		this.entryNodeIndex = entryNodeIndex;
	}

	public int getVal() {
		return exitNodeIndexes;
	}

	public void setVal(int exitNodeIndexes) {
		this.exitNodeIndexes = exitNodeIndexes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entryNodeIndex;
		result = prime * result + exitNodeIndexes;
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
		QueryPair other = (QueryPair) obj;
		if (entryNodeIndex != other.entryNodeIndex)
			return false;
		if (exitNodeIndexes != other.exitNodeIndexes)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryPair [entryNodeIndex=" + entryNodeIndex + ", exitNodeIndexes=" + exitNodeIndexes + "]";
	}

}