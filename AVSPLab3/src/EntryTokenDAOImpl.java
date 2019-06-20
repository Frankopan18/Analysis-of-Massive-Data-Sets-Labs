
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EntryTokenDAOImpl implements EntryTokenDAO {

	public EntryToken readFromFile(File inputFile) {
		EntryToken entryToken = new EntryToken();
		Scanner sc = null;
		String line = null;
		int N = 0;
		int M = 0;
		int Q = 0;
		int counter = 0;
		// variable for keeping all lines
		List<String> matrixLines = new LinkedList<>();
		// variable for all queries
		List<TokenQuery> queryLines = new LinkedList<>();
		try {
			sc = new Scanner(inputFile);
			while (sc.hasNextLine()) {
				line = sc.nextLine();

				if (counter == 0) {
					String[] parts = line.split(" ");
					N = Integer.parseInt(parts[0]);
					M = Integer.parseInt(parts[1]);
					counter++;
					continue;
				} else if (counter < N + 1) {
					counter++;
					matrixLines.add(line);
					continue;
				} else if (counter == N + 1) {
					Q = Integer.parseInt(line);
					counter++;
					continue;
				} else {
					String[] parts = line.split(" ");
					TokenQuery singleQuery = new TokenQuery(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]),
							Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
					queryLines.add(singleQuery);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}
		entryToken.setM(M);
		entryToken.setN(N);
		entryToken.setQ(Q);
		entryToken.setMatrixLines(matrixLines);
		entryToken.setQueryLines(queryLines);
		return entryToken;
	}

	@Override
	public List<Double> collaborativeFiltering(EntryToken token) {
		List<Double> solutions = new LinkedList<>();
		Matrix userItemMatrix = insertMatrix(token);
		double singleSolution = 0.0;
		for (TokenQuery singleQuery : token.getQueryLines()) {
			if (singleQuery.getT() == 0) {
				singleSolution = itemItemCollaborativeFiltering(userItemMatrix, singleQuery);
				solutions.add(singleSolution);
				
			} else if (singleQuery.getT() == 1) {
				singleSolution = userUserCollaborativeFiltering(userItemMatrix, singleQuery);
				solutions.add(singleSolution);
			}
		}
		return solutions;
	}

	private double userUserCollaborativeFiltering(Matrix userItemMatrix, TokenQuery singleQuery) {
		double singleSolution = .0;
		Matrix itemUserMatrix = userItemMatrix.transpose();
		int I = singleQuery.getI();
		int J = singleQuery.getJ();
		singleQuery.setI(J);
		singleQuery.setJ(I);
		List<Double> meanOfRows = calculateMeans(itemUserMatrix);
		Matrix subtractedMatrix = getNewSubtractedMatrix(itemUserMatrix, meanOfRows);
		List<Matrix> vectorsOfRows = getRowsOfMatrix(subtractedMatrix);
		List<Double> cosineSimiliarities = calculateSimiliarities(vectorsOfRows, singleQuery.getI());
		Map<Double, Integer> actualRowsIndexes = getRowIndexes(cosineSimiliarities);
		Collections.sort(cosineSimiliarities);
		Collections.reverse(cosineSimiliarities);
		singleSolution = calculateSolution(cosineSimiliarities, itemUserMatrix, singleQuery, actualRowsIndexes);
		return singleSolution;
	}

	private double itemItemCollaborativeFiltering(Matrix userItemMatrix, TokenQuery singleQuery) {
		double singleSolution = .0;
		List<Double> meanOfRows = calculateMeans(userItemMatrix);
		Matrix subtractedMatrix = getNewSubtractedMatrix(userItemMatrix, meanOfRows);
		List<Matrix> vectorsOfRows = getRowsOfMatrix(subtractedMatrix);
		List<Double> cosineSimiliarities = calculateSimiliarities(vectorsOfRows, singleQuery.getI());
		Map<Double, Integer> actualRowsIndexes = getRowIndexes(cosineSimiliarities);
		Collections.sort(cosineSimiliarities);
		Collections.reverse(cosineSimiliarities);
		singleSolution = calculateSolution(cosineSimiliarities, userItemMatrix, singleQuery, actualRowsIndexes);
		return singleSolution;
	}

	private Map<Double, Integer> getRowIndexes(List<Double> cosineSimiliarities) {
		Map<Double, Integer> realIndexes = new LinkedHashMap<>();
		int index = 0;
		for (Double cosineSimiliarity : cosineSimiliarities) {
			realIndexes.put(cosineSimiliarity, index++);
		}
		return realIndexes;
	}

	private double calculateSolution(List<Double> cosineSimiliarities, Matrix userItemMatrix, TokenQuery singleQuery,
			Map<Double, Integer> actualRowIndexes) {
		double solution = .0;
		int k = singleQuery.getK();
		double nominator = .0;
		double denominator = .0;
		for (Double cosineSimiliarity : cosineSimiliarities) {
			if (k == 0) {
				break;
			}
			if (cosineSimiliarity.intValue() != 1 && cosineSimiliarity > 0) {
				try {
					if ( userItemMatrix.getElementInMatrix(actualRowIndexes.get(cosineSimiliarity), (singleQuery.getJ() - 1)).intValue() == 15){
						continue;
					}
					nominator += cosineSimiliarity * userItemMatrix
							.getElementInMatrix(actualRowIndexes.get(cosineSimiliarity), (singleQuery.getJ() - 1));

					denominator += cosineSimiliarity;
				} catch (MatrixException e) {
					e.printStackTrace();
				}
				k--;
			}
		}
		solution = nominator / denominator;
		return solution;
	}

	private List<Double> calculateSimiliarities(List<Matrix> vectorsOfRows, int I) {
		List<Double> cosineSimiliarities = new LinkedList<>();
		Matrix comparedRow = vectorsOfRows.get((I - 1));
		for (Matrix singleVector : vectorsOfRows) {
			cosineSimiliarities.add(calculateSingleSimiliarity(singleVector, comparedRow));
		}
		return cosineSimiliarities;
	}

	private double calculateSingleSimiliarity(Matrix singleVector, Matrix comparedRow) {
		int i = 0;
		double ab = .0;
		for (int j = 0; j < singleVector.getCols(); j++) {
			if ((int) singleVector.getData()[i][j] == 15 || (int) comparedRow.getData()[i][j] == 15) {
				ab += 0;
			} else {
				ab += (singleVector.getData()[i][j] * comparedRow.getData()[i][j]);
			}
		}
		double straightA = getStraight(singleVector);
		double straightB = getStraight(comparedRow);
		double lastResult = ab / (straightA * straightB);
		if (lastResult >= 0.999) {
			lastResult = 1.0;
		}
		return lastResult;
	}

	private double getStraight(Matrix comparedRow) {
		double result = .0;
		for (int j = 0; j < comparedRow.getCols(); j++) {
			if ((int) comparedRow.getData()[0][j] != 15) {
				result += Math.pow(comparedRow.getData()[0][j], 2);
			}
		}
		return Math.sqrt(result);
	}

	private List<Matrix> getRowsOfMatrix(Matrix userItemMatrix) {
		List<Matrix> vectorsOfRows = new LinkedList<>();
		for (int i = 0; i < userItemMatrix.getRows(); i++) {
			Matrix a = new Matrix(1, userItemMatrix.getCols());
			double[][] data = new double[1][userItemMatrix.getCols()];
			for (int j = 0; j < userItemMatrix.getCols(); j++) {
				data[0][j] = userItemMatrix.getData()[i][j];
			}
			a.setData(data);
			vectorsOfRows.add(a);
		}
		return vectorsOfRows;
	}

	private Matrix getNewSubtractedMatrix(Matrix itemUserMatrix, List<Double> meanOfRows) {
		Matrix subtractedMatrix = new Matrix();
		double[][] data = new double[itemUserMatrix.getRows()][itemUserMatrix.getCols()];
		subtractedMatrix.setCols(itemUserMatrix.getCols());
		subtractedMatrix.setRows(itemUserMatrix.getRows());
		for (int i = 0; i < itemUserMatrix.getRows(); i++) {
			for (int j = 0; j < itemUserMatrix.getCols(); j++) {
				if ((int) itemUserMatrix.getData()[i][j] != 15) {
					data[i][j] = itemUserMatrix.getData()[i][j] - meanOfRows.get(i);
				} else {
					data[i][j] = itemUserMatrix.getData()[i][j]; // i.e.--> -1
				}
			}
		}
		subtractedMatrix.setData(data);
		return subtractedMatrix;
	}

	private List<Double> calculateMeans(Matrix itemUserMatrix) {
		List<Double> means = new LinkedList<>();
		double singleMean = .0;
		int singleCounter = 0;
		for (int i = 0; i < itemUserMatrix.getRows(); i++) {
			singleMean = .0;
			singleCounter = 0;
			for (int j = 0; j < itemUserMatrix.getCols(); j++) {
				if ((int) itemUserMatrix.getData()[i][j] != 15) {
					singleMean += itemUserMatrix.getData()[i][j];
					singleCounter++;
				}
			}
			singleMean /= singleCounter;
			means.add(singleMean);
		}
		return means;
	}

	private Matrix insertMatrix(EntryToken token) {
		Matrix returnMatrix = new Matrix();
		int numOfRows = token.getN();
		int numOfCols = token.getM();
		int i = 0;
		double[][] data = new double[numOfRows][numOfCols];
		List<String> lines = token.getMatrixLines();
		for (String line : lines) {
			String[] parts = line.split(" ");
			for (int j = 0; j < token.getM(); j++) {
				if (parts[j].equals("X")) {
					data[i][j] = 15;
				} else {
					data[i][j] = Integer.parseInt(parts[j]);
				}
			}
			i++;
		}
		returnMatrix.setCols(numOfCols);
		returnMatrix.setRows(numOfRows);
		returnMatrix.setData(data);
		return returnMatrix;
	}

}
