public interface MatrixOperations {
	
	Double getElementInMatrix(int rowIndex, int colIndex) throws MatrixException;
	
	Matrix transpose();
	
	Matrix multiply(Matrix m2);

	boolean isEqual(Matrix m2);
	
}
