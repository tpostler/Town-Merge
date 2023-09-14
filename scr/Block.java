/**
 * Block
 * Will hold the coordinates, object in the space (if there is nothing in the 
 * space then it could hold null)
 * 
 * @author Tori Postler
 * @version 0.2.0
 */
public class Block {
    /* dimension related  */
    public int location, x1, x2, y1, y2, x3, y3, x4, y4, width, height;
    /* What obj in block */
    public Anything objInSpace;

    public Block(int location, int x1, int y1, int width, int height, Anything objInSpace) {
        this.location   = location;
        this.x1         = x1;
        this.y1         = y1;
        this.width      = width;
        this.height     = height;
        this.objInSpace = objInSpace;

        // now getting the rest of the coordinates:
        x2 = x1 + width;
        y2 = y1;
        x3 = x1;
        y3 = y1 + height;
        x4 = x1 + width;
        y4 = y1 + height;
    }
    /**
     * returnCoords
     * return the coords mainly used for debugging
     * @return string of all of coords
     */
    public String returnCoords() {
        return "x1: " + x1 + " y1: " + y1 + "\nx2: " + x2 + " y2: " + y2 + 
               "\nx3: "  + x3 + " y3: " + y3 + "\nx4: " + x4 + " y4: " + y4;
    }
    /**
     * addObject2Space
     * adding an obj to the block
     * @param obj : obj to be added
     */
    public void addObject2Space(Anything obj) {
        objInSpace = obj;
    }
    /**
     * toString
     * prints what is in the block
     */
    @Override
    public String toString() {
        if(objInSpace.id != "NA")
            return objInSpace.id;
        else
            return "Block Empty";
    }
}
