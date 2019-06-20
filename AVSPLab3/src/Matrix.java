import java.text.DecimalFormat;
import java.util.Arrays;

public class Matrix implements MatrixOperations {

	private double[][] data = null;
	private int rows;
	private int cols;
	private int fileSeparation = 0; // 0 says it's space, 1 says it's tab

	public Matrix(double[][] data, int rows, int cols) {
		this.data = data;
		this.cols = cols;
		this.rows = rows;
		fileSeparation = 0;
	}

	public Matrix(int rows, int cols) {
		this.data = null;
		this.cols = cols;
		this.rows = rows;
		fileSeparation = 0;
	}

	public Matrix() {
		fileSeparation = 0;
	}

	public double[][] getData() {
		return data;
	}

	public void setData(double[][] data) {
		this.data = data;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public int getFileSeparation() {
		return fileSeparation;
	}

	public void setFileSeparation(int fileSeparation) {
		this.fileSeparation = fileSeparation;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cols;
		result = prime * result + Arrays.deepHashCode(data);
		result = prime * result + rows;
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
		Matrix other = (Matrix) obj;
		if (cols != other.cols)
			return false;
		if (!Arrays.deepEquals(data, other.data))
			return false;
		if (rows != other.rows)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Matrix [data=" + Arrays.toString(data) + ", rows=" + rows + ", cols=" + cols + "]";
	}

	/**
	 * Used for printing matrix to a standard output
	 * 
	 * @return matrix in a form of 2D array
	 */
	public void printMatrix() {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(6);
		System.out.println("Printed matrix is : \n");
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if (this.fileSeparation == 0) {
					System.out.print(df.format(this.data[i][j]) + " ");
				} else {
					System.out.print(df.format(this.data[i][j]) + "\t");
				}
			}
			System.out.println();
		}
	}

	/**
	 * @param rowIndex index of the row of the element starting from 0
	 * @param colIndex index of the column of the element starting from 0
	 * @return double element from the 2D array (Matrix)
	 */
	public Double getElementInMatrix(int rowIndex, int colIndex) throws MatrixException {
		if (rowIndex <= this.rows && colIndex <= this.cols) {
			return this.data[rowIndex][colIndex];
		} else {
			throw new MatrixException("Index of the matrix element is out of the given range!");
		}
	}

	/**
	 * Used in some other matrix operations Transposition of the matrix where rows
	 * become columns and columns the rows
	 * 
	 * @return transposed matrix A
	 */
	public Matrix transpose() {
		Matrix retMatrix = new Matrix(new double[this.cols][this.rows], this.cols, this.rows);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				retMatrix.data[j][i] = this.data[i][j];
			}
		}
		return retMatrix;
	}

	/**
	 * Used for multiplying matrix to a single value
	 * 
	 * @param scalar value with whom we multiply all other elements in the matrix
	 * @return matrix multiplied by the scalar value
	 */
	public Matrix multiplyScalar(double scalar) {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.data[i][j] *= scalar;
			}
		}
		return this;
	}

	/**
	 * Used for checking equality of matrices
	 * 
	 * @param m2 Matrix which we use to check equality of the initial matrix
	 * @return boolean value if they are equal and false if not
	 */
	public boolean isEqual(Matrix m2) {
		if (this.rows != m2.rows || this.cols != m2.cols)
			try {
				throw new MatrixException("Dimensions do not match.");
			} catch (MatrixException e) {
				e.printStackTrace();
			}
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if (this.data[i][j] != m2.data[i][j])
					return false;
			}
		}
		return true;
	}

	/**
	 * Identity matrix is matrix where all elements are 0 besides ones on the main
	 * diagonal
	 * 
	 * @param length of the matrix
	 * @return matrix of the identity
	 */
	public static Matrix getMatrixIdentity(int length) {
		Matrix I = new Matrix(length, length);
		I.data = new double[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (i == j) {
					I.data[i][j] = 1;
				} else {
					I.data[i][j] = 0;
				}
			}
		}
		return I;
	}

	@Override
	public Matrix multiply(Matrix m2) {
		if (this.cols != m2.rows) {
			try {
				throw new MatrixException("Can't multiply because dimensions do not fit!");
			} catch (MatrixException e) {
				e.printStackTrace();
			}
		}
		Matrix m3 = new Matrix(new double[this.rows][m2.cols], this.rows, m2.cols);
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < m2.cols; j++) {
				for (int k = 0; k < this.cols; k++) {
					m3.data[i][j] += this.data[i][k] * m2.data[k][j];
				}
			}
		}
		return m3;
	}
}
