package FileSystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by markus on 01.06.15.
 */
public class Folder extends FSObject {


    private LinkedList<FSObject>contents = new LinkedList<>();


    public Folder() {

    }

    public void add(FSObject e) {
        contents.add(e);
    }


}
