import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * Anything
 * The obj that usr interacts. Its mostly img related things
 * @author Tori Postler
 * @version 0.2.0
 */

public class Anything {
    /* Regular Size */
    int OBJ_WIDTH = 130;
    int OBJ_HEIGHT = 115;

    /** Other variables */
    String    img,  id,   next, nextImg;
    int       xLoc, yLoc, blockPlace;
    JLabel    anything = new JLabel(""); // where image will be placed

    private int score; // score relating to id
    Image icon = null;
    BufferedImage buffImg = null; // how the image will be loaded, need for resizable
    /**
     * Anything
     * @param iden       : the name/id of obj (ex. bush)
     * @param nextItem   : what the next thing will make when get a set, if 
     *                     make nothing then will be NA
     * @param beforeItem : what obj are needed to make item
     * @param image      : the file name of image that corresponds to obj
     * @param nextImage  : what img comes after
     * @param x          : x coord in window
     * @param y          : y coord in window
     * @param bPlace     : place in grid, spaces start at top left corner (0)
     *                     ending at botttom right corner (24)
     * @param scr        : score that coresponds to id
     */
    Anything(String iden, String nextItem, String beforeItem, String image, String nextImage,
             int x, int y, int bPlace, int scr) {
        id         = iden;
        img        = image;
        nextImg    = nextImage;
        xLoc       = x;
        yLoc       = y;
        blockPlace = bPlace;
        score      = scr;

        anything.setBounds(xLoc, yLoc, OBJ_WIDTH, OBJ_HEIGHT);

                try {
                    icon = ImageIO.read(this.getClass().getResourceAsStream(img));
                }
                catch(Exception e) {
                }
                
            anything.setIcon(new ImageIcon(icon));
    }
    /**
     * returnLabel
     * @return the obj's label, needed to add to window
     */
    public JLabel returnLabel() {
            return anything; 
    }
    /**
     * updateCoord
     * updating the coordinates for the obj, will change the bounds for the label
     */
    public void updateCoords(int x, int y) {
        anything.setBounds(x, y, OBJ_WIDTH, OBJ_HEIGHT);
    }
    /**
     * updateImage
     * updating the image attacted to the label, changing the ImageIcon for the label
     */
    public void updateImage() {
        upgrade();
        try {
            icon = ImageIO.read(this.getClass().getResourceAsStream(nextImg));
        }
        catch(Exception e) {}
        anything.setIcon(new ImageIcon(icon));
    }
    /**
     * toString
     * will tell you the id of the block, might consider updating to also give
     * the image path, for degbuggin
     */
    @Override
    public String toString() {
        return id;
    }
    /**
     * returnID
     * returning the id for the anythijg
     * @return id : id/name of the obj
     */
    public String returnID() {
        return id;
    }
    /**
     * updateBlockPlace
     * updating where the obj is in the block list
     */
    public void updateBlockPlace(int place) {
        blockPlace = place;
    }
    /**
     * updateID
     * updating nad changing the id of the obj
     */
    public void updateID(String id) {
        this.id = id;
    }
    /**
     * nextId
     * it changes the id to the next thing that it merges
     * Flow of objects:
     * grass > bush > tree > hut > house > mansion > castle > floating castle > triple castle
     */
    public void nextId() {
        if(id.equals("grass"))
            id = "bush";

        else if(id.equals("bush"))
            id = "tree";
        
        else if(id.equals("tree"))
            id = "hut";
        
        else if(id.equals("hut"))
            id = "house";
        
        else if(id.equals("house"))
            id = "mansion";

        else if(id.equals("mansion"))
            id = "castle";

        else if(id.equals("castle"))
            id = "floatingCastle";        
    }
    /**
     * upgrade
     * will change the image so when it upgrades and merges it will change the image
     */
    public void upgrade() {
        switch(id) {
        case "bush":
            nextImg = "Resources/Images/tree.png";
            break;
        
        case "tree":
            nextImg = "Resources/Images/hut.png";
        break;

        case "hut": 
            nextImg = "Resources/Images/house.png";
            break;

        case "house":
            nextImg = "Resources/Images/mansion.png";
            break;

        case "mansion":
            nextImg = "Resources/Images/castle.png";
            break;
        
        case "castle":
            nextImg = "Resources/Images/floatingCastle.png";
            break;
        default: break;
        }
    }
    /**
     * returnScore
     * returns the score
     */
    public int returnScore(){
        return score;
    }
    /**
     * updateScore
     * updates the score coresspoding to the id
     */
    public void updateScore() {
        switch(id) {
            case "bush":
                score = 10;
                break;
            
            case "tree":
                score = 50;
            break;
    
            case "hut": 
                score = 100;
                break;
    
            case "house":
                score = 150;
                break;
    
            case "mansion":
                score = 300;
                break;
            
            case "castle":
                score = 500;
                break;
    
            case "floatingCastle":
                score = 1000;
                break;

            default: break;
        }
    }
}