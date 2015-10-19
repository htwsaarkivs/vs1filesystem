package htw.vs1.filesystem.GUI.help;

import htw.vs1.filesystem.FileSystemManger;
import htw.vs1.filesystem.GUI.Tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The HelpFileThread is used to copy the help files from
 * the resource folder in the jar-archive into a temp
 * file asynchronously.
 *
 * Created by felix on 19.10.15.
 */
public class HelpFileThread extends Thread {

    private static final String PATH_TO_PDF = "../resources/GUIHelp.pdf";

    @Override
    public void run() {

        try {
            InputStream inputStream = getClass().getResourceAsStream(PATH_TO_PDF);
            File tmpFile = File.createTempFile("helpVFiles", ".pdf");
            FileOutputStream outputStream = new FileOutputStream(tmpFile);
            Tools.copyStream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
            outputStream.close();
            FileSystemManger.getInstance().setHelpFile(tmpFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
