import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class CF {

	public static void main(String[] args) {
		File inputFile = new File("src/R1.txt");
		EntryTokenDAO dao = new EntryTokenDAOImpl();
		EntryToken entryToken = dao.readFromFile(inputFile);
		List<Double> solutions = dao.collaborativeFiltering(entryToken);
		for (Double solution : solutions){
			DecimalFormat df = new DecimalFormat("#.000");
			BigDecimal bd = new BigDecimal(solution);
			BigDecimal res = bd.setScale(3, RoundingMode.HALF_UP);
			System.out.println(df.format(res));
		}
	}
}
