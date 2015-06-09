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

        if (args.length != 1) {
            System.out.println("Usage: NioTrial <path-to-directory>");
            System.exit(0);
        }

        fileSystemTreeRecursion(Files.newDirectoryStream(Paths.get(args[0])), 0);
    }

    private static void fileSystemTreeRecursion(DirectoryStream<Path> directoryStream, int iterDepth) throws IOException {
        for (Path path : directoryStream) {
            System.out.println(getTab(iterDepth) + path.toAbsolutePath().toString());

            if (path.toFile().isDirectory()) {
                fileSystemTreeRecursion(Files.newDirectoryStream(Paths.get(path.toAbsolutePath().toString())), iterDepth+1);
            }
        }
    }

    private static String getTab(int depth) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            builder.append("\t");
        }
        return builder.toString();
    }

}
