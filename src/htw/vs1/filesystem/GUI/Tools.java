package htw.vs1.filesystem.GUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Felix on 18.10.2015.
 */
public class Tools {

    public static void copyStream( InputStream inputStream, OutputStream outputStream ) throws IOException {
        try
        {
            byte  buffer[] = new byte[0xffff];
            int   numBytes;

            while ( (numBytes = inputStream.read(buffer)) != -1 ) {
                outputStream.write(buffer, 0, numBytes);
            }
        }
        finally {
            if ( inputStream != null )
                try {
                    inputStream.close();
                } catch ( IOException e ) {}

            try {
                if ( outputStream != null )
                    outputStream.close();
            } catch ( IOException e ) {}
        }
    }

}
