package mas.diseasespread.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVWriter {

  public void writeCSV(List<String[]> data, String fileId) {
    File outputFile = new File(fileId + ".csv");
    try (PrintWriter writer = new PrintWriter(outputFile)) {
      data.stream()
        .map(this::convertToCSV)
        .forEach(writer::println);
    } catch (FileNotFoundException fnfe) {
      fnfe.printStackTrace();
    }
  }

  public void writeCSVFromList(List<List<String>> data, String fileId) {
    List<String[]> arrList = new ArrayList<>();
    for (List<String> datum : data) {
      arrList.add((String[]) datum.toArray());
    }
    writeCSV(arrList, fileId);
  }

  private String convertToCSV(String[] data) {
    return Stream.of(data)
      .map(this::escapeSpecialCharacters)
      .collect(Collectors.joining(","));
  }

  private String escapeSpecialCharacters(String data) {
    String escapedData = data.replaceAll("\\R", " ");
    if (data.contains(",") || data.contains("\"") || data.contains("'")) {
      data = data.replace("\"", "\"\"");
      escapedData = "\"" + data + "\"";
    }
    return escapedData;
  }
}
