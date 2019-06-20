
import java.util.List;

public class EntryToken {

	private int N;

	private double s;

	private int b;

	private List<Basket> baskets;

	public EntryToken(int n, double s, int b, List<Basket> baskets) {
		super();
		N = n;
		this.s = s;
		this.b = b;
		this.baskets = baskets;
	}

	public EntryToken() {
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public double getS() {
		return s;
	}

	public void setS(double s) {
		this.s = s;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public List<Basket> getBaskets() {
		return baskets;
	}

	public void setBaskets(List<Basket> baskets) {
		this.baskets = baskets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + N;
		result = prime * result + b;
		result = prime * result + ((baskets == null) ? 0 : baskets.hashCode());
		long temp;
		temp = Double.doubleToLongBits(s);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (b != other.b)
			return false;
		if (baskets == null) {
			if (other.baskets != null)
				return false;
		} else if (!baskets.equals(other.baskets))
			return false;
		if (Double.doubleToLongBits(s) != Double.doubleToLongBits(other.s))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EntryToken [N=" + N + ", s=" + s + ", b=" + b + ", baskets=" + baskets + "]";
	}

}