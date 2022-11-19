package mas.diseasespread.io;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileFinder {

  public static boolean emptyDirectory(String dir) throws IOException {
    try (Stream<Path> stream = Files.list(Paths.get(dir))) {
      return !stream.iterator().hasNext();
    }
  }

  public static List<File> getFiles(String dir) throws IOException {
    return Files.list(Paths.get(dir))
        .map(Path::toFile)
        .collect(Collectors.toList());
  }

  public static List<String> getFileNames(String dir) throws IOException {
    List<File> files = getFiles(dir);
    return files.stream()
        .map(File::getName)
        .collect(Collectors.toList());
  }

  public static boolean directoryContainsFile(String dir, String file) throws IOException {
    if (!Files.exists(Paths.get(dir)))
      return false;
    return getFileNames(dir).contains(file);
  }

}
