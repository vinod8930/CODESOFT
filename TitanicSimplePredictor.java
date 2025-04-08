import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TitanicSimplePredictor {

    public static void main(String[] args) {
        String csvFile = "C:/Users/vinod/Downloads/Titanic-Dataset.csv";
        String line;
        int total = 0;
        int correct = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String header = br.readLine(); // Read header line
            String[] columns = header.split(",");

            int survivedIdx = getIndex(columns, "Survived");
            int sexIdx = getIndex(columns, "Sex");
            int pclassIdx = getIndex(columns, "Pclass");

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < Math.max(survivedIdx, Math.max(sexIdx, pclassIdx))) continue;

                String actualSurvived = values[survivedIdx];
                String sex = values[sexIdx].trim();
                String pclass = values[pclassIdx].trim();

                // Basic rule-based prediction:
                // if female and not in 3rd class => predict survived
                // else predict not survived
                String prediction = (sex.equalsIgnoreCase("female") && !pclass.equals("3")) ? "1" : "0";

                if (prediction.equals(actualSurvived)) {
                    correct++;
                }
                total++;
            }

            System.out.println("Total records evaluated: " + total);
            System.out.println("Correct predictions: " + correct);
            System.out.println("Accuracy: " + (100.0 * correct / total) + "%");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper to find column index
    private static int getIndex(String[] headers, String columnName) {
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].trim().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }
}
