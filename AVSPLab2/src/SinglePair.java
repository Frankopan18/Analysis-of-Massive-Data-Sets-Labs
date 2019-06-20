

public class SinglePair {

	private int I;

	private int K;

	public SinglePair(int i, int k) {
		super();
		I = i;
		K = k;
	}

	public SinglePair() {

	}

	public int getI() {
		return I;
	}

	public void setI(int i) {
		I = i;
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + I;
		result = prime * result + K;
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
		SinglePair other = (SinglePair) obj;
		if (I != other.I)
			return false;
		if (K != other.K)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SinglePair [I=" + I + ", K=" + K + "]";
	}

}
