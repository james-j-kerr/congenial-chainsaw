package mas.diseasespread.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVReader {

  public static List<List<String>> readCSV(String pathToFile) {
    List<List<String>> records = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] record = line.split(",");
        records.add(Arrays.asList(record));
      }

    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    return records;
  }

}
