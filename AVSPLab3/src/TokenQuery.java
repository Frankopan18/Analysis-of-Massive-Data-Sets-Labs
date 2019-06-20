
public class TokenQuery {

	private int I;
	private int J;
	private int T;
	private int K;

	public TokenQuery(int i, int j, int t, int k) {
		super();
		I = i;
		J = j;
		T = t;
		K = k;
	}

	public TokenQuery() {

	}

	public int getI() {
		return I;
	}

	public void setI(int i) {
		I = i;
	}

	public int getJ() {
		return J;
	}

	public void setJ(int j) {
		J = j;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
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
		result = prime * result + J;
		result = prime * result + K;
		result = prime * result + T;
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
		TokenQuery other = (TokenQuery) obj;
		if (I != other.I)
			return false;
		if (J != other.J)
			return false;
		if (K != other.K)
			return false;
		if (T != other.T)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenQuery [I=" + I + ", J=" + J + ", T=" + T + ", K=" + K + "]";
	}

}
