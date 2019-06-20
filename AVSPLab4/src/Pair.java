import java.util.List;

public class Pair {

	private int entryNodeIndex;
	private List<Integer> exitNodeIndexes;

	public Pair(int entryNodeIndex, List<Integer> exitNodeIndexes) {
		super();
		this.entryNodeIndex = entryNodeIndex;
		this.exitNodeIndexes = exitNodeIndexes;
	}

	public Pair() {

	}

	public int getEntryNodeIndex() {
		return entryNodeIndex;
	}

	public void setEntryNodeIndex(int entryNodeIndex) {
		this.entryNodeIndex = entryNodeIndex;
	}

	public List<Integer> getExitNodeIndexes() {
		return exitNodeIndexes;
	}

	public void setExitNodeIndexes(List<Integer> exitNodeIndexes) {
		this.exitNodeIndexes = exitNodeIndexes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + entryNodeIndex;
		result = prime * result + ((exitNodeIndexes == null) ? 0 : exitNodeIndexes.hashCode());
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
		Pair other = (Pair) obj;
		if (entryNodeIndex != other.entryNodeIndex)
			return false;
		if (exitNodeIndexes == null) {
			if (other.exitNodeIndexes != null)
				return false;
		} else if (!exitNodeIndexes.equals(other.exitNodeIndexes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pair [entryNodeIndex=" + entryNodeIndex + ", exitNodeIndexes=" + exitNodeIndexes + "]";
	}

}