

public class SpecialPair {

	private int[] binaryRepresentation;

	private String hexRepresentation;

	public SpecialPair(int[] binaryRepresentation, String hexRepresentation) {
		super();
		this.binaryRepresentation = binaryRepresentation;
		this.hexRepresentation = hexRepresentation;
	}

	public SpecialPair() {

	}

	public int[] getBinaryRepresentation() {
		return binaryRepresentation;
	}

	public void setBinaryRepresentation(int[] binaryRepresentation) {
		this.binaryRepresentation = binaryRepresentation;
	}

	public String getHexRepresentation() {
		return hexRepresentation;
	}

	public void setHexRepresentation(String hexRepresentation) {
		this.hexRepresentation = hexRepresentation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((binaryRepresentation == null) ? 0 : binaryRepresentation.hashCode());
		result = prime * result + ((hexRepresentation == null) ? 0 : hexRepresentation.hashCode());
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
		SpecialPair other = (SpecialPair) obj;
		if (binaryRepresentation == null) {
			if (other.binaryRepresentation != null)
				return false;
		} else if (!binaryRepresentation.equals(other.binaryRepresentation))
			return false;
		if (hexRepresentation == null) {
			if (other.hexRepresentation != null)
				return false;
		} else if (!hexRepresentation.equals(other.hexRepresentation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SpecialPair [binaryRepresentation=" + binaryRepresentation + ", hexRepresentation=" + hexRepresentation
				+ "]";
	}

}
