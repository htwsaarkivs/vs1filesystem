package htw.vs1.filesystem.Trials;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by felix on 09.06.15.
 */
public class NioTrial {

    public static void main(String[] args) throws Exception {
        List<String> fileNames = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("/Users/felix/htw"))) {
            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }
        } catch (IOException ex) {}


        for (String fileName : fileNames) {
            System.out.println(fileName + "\n");
        }

    }

}
