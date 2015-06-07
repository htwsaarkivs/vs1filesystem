package htw.vs1.filesystem.FileSystem;

import java.util.LinkedList;

/**
 * A FSObject represents a object in our filesystem.
 * It can either be a {@link Folder}, which can contain other
 * {@link FSObject}s, or a {@link File}, which is a leaf of the
 * filesystem.
 *
 * Created by markus on 01.06.15.
 */
public abstract class FSObject {

    private String name;

    /**
     * Creates a new FSObject.
     *
     * @param name name of the FSObject.
     */
    public FSObject(String name) {
        setName(name);
    }

    /**
     * Set the name of this {@link FSObject}.
     *
     * @param name new name of this object.
     */
    private void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of this {@link FSObject}.
     *
     * @return name of this object.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
    public LinkedList<FSObject> search ( String name){
    return search(new LinkedList<FSObject>(),name);
    
    }
    
    protected LinkedList<FSObject> search (LinkedList<FSObject> list, String name){
        //Wenn mein name schmeiß ihn da rein!!!!
        if (this.getName().equals(name)){
            list.add(this);
        }
        
        
        return list;
    
    }



}
