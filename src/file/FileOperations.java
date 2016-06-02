package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jan on 02.06.2016.
 */
public class FileOperations {

    public static void hashMapToCSVFile(String filePath, HashMap<?, ?> hashMap) {
        Path path = Paths.get(filePath);

        try {
            if(Files.exists(path)) {
                Files.delete(path);
            }

            String line;
            for(Map.Entry<?, ?> entry: hashMap.entrySet()) {
                line = entry.getKey().toString() + ";" + entry.getValue().toString() + "\n";
                Files.write(path, line.getBytes("utf-8"), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> CSVFileToHashMap(String filePath) {
        Path path = Paths.get(filePath);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String csvSeparator = ";";

        if(Files.exists(path)) {
            try{
                Files.lines(path).map(s -> s.split(csvSeparator)).forEach(s -> hashMap.put(s[0], s[1]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file found...");
        }
        return hashMap;
    }

    public static boolean checkIfCSVExists(String filePath) {
        Path path = Paths.get(filePath);
        if(Files.exists(path)) {
            return true;
        } else {
            return false;
        }
    }
}
