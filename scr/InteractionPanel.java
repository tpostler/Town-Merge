import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

/**
 * InteractionPanel
 * I think you could also call this the game engine... (not sure that lingo thou)
 * 
 * Where the game takes place. Board gets populated with diff obj and a diff
 * obj gets generated and will follow where the usr's mouse goes until usr clicks
 * on tile where obj will drop and check around if there is a set of 3 to merge.
 * This cycle will contintue to go until there are no spaces left on the board
 * when the usr will have to manually reset
 * 
 * KNOWN BUGS:
 * - Based on the math/calculations that program uses to check for set of 3
 *   it will sometimes merge obj not next to each other. For instance there could
 *   be 2 objs on the far right side of a row and on the next row on the far left
 *   side it will merge.
 * 
 * POSSIBLE SOLUTIONS:
 * - set and if statement to fix
 * 
 * @author Tori Postler
 * @version 0.2.0
 */
class InteractionPanel extends JPanel implements MouseListener, MouseMotionListener{
    /* Dimensions */
    int PANEL_HEIGHT;
    int PANEL_WIDTH;
    
    /* size of objs */
    int sizeVal = 50;
    
    /* Score vars */
    int    score = 0;
    JLabel scoreLabel; // backgroundImage
    
    /* how mouse listener gets disabled cause of help btn */
    boolean disabled = false;

    
    
    /* Relating to obj that gets moved with usr cursor */
    Anything usrObj    = null;  // where obj is stored when getting moved around
    int      usrXCoord;         // self explanatory
    int      usrYCoord;

    /* Accounts for double merges in turn (further explained later) */
    boolean done = false;

    /* acts as a placeholder when transfering usr obj to merging method (explained later) */
    Anything testerObj;
    
    /* things that can generated initally, amount of times sometimes up relates
       to how more likely it is to show up, i have no logic to how I did this */
    String[] avaObj = new String[] { "grass", "bush", "tree", "hut", "hut",
                                     "hut",   "hut",  "NA"};

    /* same thing as a above but this is what gets generated to be on usr mouse */
    String[] placeableObj = new String[] {"grass", "bush", "tree", "hut", 
                                          "tree", "hut"};

    /* Variables relating to Block objs... */
    Block blockList[] = new Block[25]; // easily accessible block data
    int   B_WIDTH     = 132;           // width of each block
    int   B_HEIGHT    = 110;           // height of each block
    int[] xCoord      = new int[25];   // x-coords for each block  
    int[] yCoord      = new int[25];   // y-coords ^
    
    /**
     * InteractionPanel
     * Constructor: sets up score label, the panel itself, gets the coordinates,
     * and the board all set up. Then from there all usr actions driven (don't 
     * think i used that correctly)
     */
    InteractionPanel() {
        /* Panel Setup (Including adding mouseListeners*/
        this.setBounds(20, 100, 660, 690);
        this.setVisible(true); 
        this.setOpaque(false);
        this.setLayout(null);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        /* Adding Score label */
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 30));
        scoreLabel.setBounds(0, 0, 300, 30);
        this.add(scoreLabel);

        /* createCoords -> sets up block coordinates, populatre -> gen obj on board */
        createCoords();
        populate(false);
    }
    /**
     * selectObj
     * Randomly selects obj that usr will have to place
     */
    public void selectObj() {
        /* Rnadomly picking from placable obj list */
        // int place =  [new Random().nextInt(placeableObj.length)
        String holderObj= placeableObj[new Random().nextInt(placeableObj.length)];


            /** will subsequenly create Anything Obj and store in usrObj */
            switch (holderObj) {
                case "grass":
                // creating object
                usrObj = new Anything("grass", "bush", "NA", "Resources/Images/leaf.png", 
                "Resources/Images/bush.png", usrXCoord, usrYCoord, -1, 5);
                break;

                case "bush":
                usrObj = new Anything("bush", "tree", "grass", "Resources/Images/bush.png", "Resources/Images/tree.png", 
                usrXCoord, usrYCoord, -1, 10);
                break;

                case "tree":
                usrObj = new Anything("tree", "hut", "bush", "Resources/Images/tree.png", "Resources/Images/hut.png",
                usrXCoord, usrYCoord, -1, 50);
                break;

                case "hut":
                usrObj = new Anything("hut", "house", "tree", "Resources/Images/hut.png", "Resources/Images/house.png",
                usrXCoord, usrYCoord, -1, 300);
                break;
            
                default: break;
            }
        //usrObj.changeSize(sizeVal);     // changes size based on slider >=(
        this.add(usrObj.returnLabel()); // displays the image
    }
    /**
     * populate
     * adds obj to objList and labelList, once created will display in window
     * @param reset : true -> wipes board/objList/labelList, false -> skips clearing
     */
    public void populate(boolean reset) {     
        int    holderLabelPlace;    // block place in grid
        String holderObj;           // which obj getting placed
       
        /* in order to reset the whole grid, must delete and update the frame
           then can populate again */
        if(reset) {
            for (Block block : blockList) {
                if(block.objInSpace != null) {
                    this.remove(block.objInSpace.returnLabel());
                    this.validate();
                    this.repaint();
                    block.objInSpace = null;
                }
            }
        }
        /* populating blocks
           i's can be replaced as loop continutes but I'm keeping it that
           way cause that way differnt number of objs placed each time   */
        for (int i = 0; i < 25; i++) {
            // selecting rand obj from given list
            holderObj        = avaObj[new Random().nextInt(avaObj.length)];
            // selecting rand int (0-24) corressponding to block placement
            holderLabelPlace = i;

            /* adds the class of selected obj to label, obj and either
               being/inanimate obj  */
            switch (holderObj) {
                case "grass":
                // creating object
                Anything grass = new Anything("grass", "bush", "NA", "Resources/Images/leaf.png", 
                "Resources/Images/bush.png",
                              xCoord[holderLabelPlace], yCoord[holderLabelPlace], 
                              holderLabelPlace, 5);
                // assigning to block:
                blockList[holderLabelPlace].addObject2Space(grass);
                break;

                case "bush":
                Anything bush = new Anything("bush", "tree", "grass", "Resources/Images/bush.png", 
                "Resources/Images/tree.png",
                             xCoord[holderLabelPlace], yCoord[holderLabelPlace], 
                             holderLabelPlace, 10);
                blockList[holderLabelPlace].addObject2Space(bush);
                break;

                case "tree":
                Anything tree = new Anything("tree", "hut", "bush", "Resources/Images/tree.png",
                "Resources/Images/hut.png", 
                             xCoord[holderLabelPlace], yCoord[holderLabelPlace],
                             holderLabelPlace, 50);
                blockList[holderLabelPlace].addObject2Space(tree);
                break;

                default:
                // must do this becuase if not could get null pointer exception
                Anything nothing = new Anything("NA", "", "", "Resources/Images/blank.png", "Resources/Images/blank.png",
                xCoord[holderLabelPlace], yCoord[holderLabelPlace], holderLabelPlace, 0);
                blockList[holderLabelPlace].addObject2Space(nothing);
                break;
            }
        }
        for(Block block : blockList) { 
            if(block.objInSpace.id != "NA")
                this.add(block.objInSpace.returnLabel());
            //else if(block.objInSpace == null) {}
        }  
    }
    /**
     * createCoords
     * puts all of the x and y values in a list for blockList to use
     * I got these numbers myself one day I will change them so more flexible
     * 
     * This will also generate all of the block objs and put in list
     */
     public void createCoords()
    {
        /* Adding x-coord, place in list is block place in board */
        for (int i = 0; i < 25; i++) {
                if (i % 5 == 0)
                    xCoord[i] = 0;
                else if (i % 5 == 1)
                    xCoord[i] = 132;
                else if (i % 5 == 2)
                    xCoord[i] = 264;
                else if (i % 5 == 3)
                    xCoord[i] = 396;
                else if (i % 5 == 4)
                    xCoord[i] = 528;
                else
                    xCoord[i] = 0;
        }
        /* Adding y-coord, place in list is block place in board */
        for (int i = 0; i < 25; i++) {
            if (i < 5 && i >= 0)
                yCoord[i] = 130;
            else if (i <= 9 && i >= 5)
                yCoord[i] = 240;
            else if (i < 15 && i > 9)
                yCoord[i] = 350;
            else if (i < 20 && i > 14)
                yCoord[i] = 460;
            else
               yCoord[i] = 570;
        }
        // CREATING THE BLOCK OBJECTS  
        int x1, y1;
        for (int i = 0; i < 25; i++) {
            x1 = xCoord[i];
            y1 = yCoord[i];
            blockList[i] = new Block(i, x1, y1, B_WIDTH, B_HEIGHT, null);
        }
    }
    /**
     * checkBlock
     * Gets coords and checks if usr clicked on empty block. If empty then will
     * put whatever obj usr has into block. Then from there will send off to 
     * other method that will check for merges (set of 3)
     * 
     * @param xCord : x-coord of usr click
     * @param yCord : y-coord of usr click
     */
    public void checkBlock(int xCord, int yCord) { 
        int x = xCord;
        int y = yCord;

        /* goes through all of the blocks and gets the bounds of each ones and
           assigns proper variables */
        for(Block block : blockList) { 
            if( (x >= block.x1 && x < block.x2) && (y >= block.y1 && y < block.y3) ) {
                // NA means empty block
                if (block.objInSpace.id == "NA") {
                    /* needs to update the usr obj before placing in block */
                    usrObj.updateCoords(xCoord[block.location], yCoord[block.location]);
                    usrObj.updateBlockPlace(block.location);
                    blockList[block.location].addObject2Space(usrObj); // adding obj into block

                    // testerObj acts as holder so that usrObj can generate new
                    // obj while merging executes 
                    testerObj = usrObj;
                    
                    usrObj = null; // WILL ALLOW FOR BLOCK TO DROP DON'T DELETE!

                    score += testerObj.returnScore();   // updating score
                    scoreLabel.setText("Score: " + score); 

                    //testerObj.changeSize(sizeVal);      // updating size
                    
                    /* This is in place for double/muliple merges this is becuase
                     * when a merge is found it will return back to this and will
                     * only stop when there is no merge found.
                     * The tester obj will update if there is/isn't a merge found,
                     * when merge found updated obj in, no merge changes to null 
                     * to end this
                     */
                    while(!done) {
                        // when there was no more merging
                        if(testerObj == null)
                            done = true;
                        // checking for merges (this will run first)
                        if(testerObj != null) 
                            check4AllSolutions(block, testerObj);
                    }
                    done = false; // gotta reset so above loop will happen
                }
            }
        }
    }
    /**
     * mouseReleased whe nthe usr lets go of there click checkBlock will be called
     * this will get disabled when help is clicked
     */
    public void mouseReleased(MouseEvent e)
    {
        if(disabled)
            return;
        else {
            int x = e.getX();
            int y = e.getY();
            // seeing if can place
            checkBlock(x, y);
        }
    }
    public void mouseMoved(MouseEvent e) {
        this.revalidate();
        this.repaint();
        if (usrObj == null) 
            selectObj();
        
        usrXCoord = e.getX();
        usrYCoord = e.getY();
        usrObj.updateCoords(usrXCoord - 50, usrYCoord - 50);
        this.revalidate();
    }
    /**
     * Method mouseEntered - mousePressed all required for MouseListener + 
     * MouseMotion listener Interface
     */
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e)  {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    /**
     * check4Merge
     * @param block    : block that usr dropped obj in (calc based on pos)
     * @param usrBlock : obj that blocks are looking for
     * @param pos1     : where 1st possible merge could be
     * @param pos2     : where 2nd possible merge could be
     * @return : int   : corresponds to if found, 1 -> found 0 -> not found
     */
    public int check4Merge(Block block, Anything usrBlock,
                               int pos1, int pos2) {
        /**
         * since blocks are based in grid I can do either addition or subtraction
         * to get spefeic place in block and since obj is stored in block then
         * can acess ids 
         */
        if( blockList[block.location + pos1].objInSpace.id == usrBlock.id && 
            blockList[block.location + pos2].objInSpace.id == usrBlock.id) 
        {
            // setting objs to be null/empty -> making block empty
            blockList[block.location + pos1].objInSpace.id = "NA";
            blockList[block.location + pos2].objInSpace.id = "NA";

            // updating the space where usr clicked to be next merge obj thing
            blockList[block.location].objInSpace.updateImage();
            blockList[block.location].objInSpace.nextId();
            blockList[block.location].objInSpace.updateScore();
            
            // add to score
            score += blockList[block.location].objInSpace.returnScore();
            scoreLabel.setText("Score: " + score);

            // need to remove label / image so that usr known block is empty
            this.remove(blockList[block.location + pos1].objInSpace.returnLabel());
            this.remove(blockList[block.location + pos2].objInSpace.returnLabel());

            // updated the testerobj to be update obj so can be used again in 
            // case of double merge
            testerObj = blockList[block.location].objInSpace;

            return 1; // found merge
        }
        else {
            testerObj = null; // need to let loop know that no merge found
            return 0;
        }
    }
    /**
     * check4AllSolutions
     * This will call check4Merge based on every case of how the block could
     * merge. If there is a merge to be found then will exit the method so that
     * the done loop will run this over again. This has to happen becuase if 
     * doesn't then it will cause for not all cases to be check in double merges
     * so have to send back (that's why the udated testerobj gets updated so that
     * it can be send again in done loop and not have to worrying about resetting
     * anyting)
     * @param block    : block that usr placed obj on
     * @param usrBlock :  obj that usr place
     */
    public void check4AllSolutions(Block block, Anything usrBlock) {
        /* if statement coresponds tto reuirments cause index out of bonds 
           be thrown. Writing this now is mkaing me realize I can do a try catch
           loop, but I'm too lazy to think about it I've been commenting almost 
           and hr and a half */
        if(block.location >= 6) {
            if(check4Merge(block, usrBlock, -6, -5) == 1) return;
            if(check4Merge(block, usrBlock, -6, -1) == 1) return;
        }
        if(block.location >= 5 && block.location <= 19)
            check4Merge(block, usrBlock, -5, 5);
        if(block.location >= 5) {
            if(check4Merge(block, usrBlock, -4, -5) == 1) return;
            if(check4Merge(block, usrBlock, -1, -5) == 1) return;
        }
        if(block.location >= 4 && block.location <= 23)
            if(check4Merge(block, usrBlock, -4, 1) == 1) return;
        if(block.location >= 1 && block.location <= 23)
            if(check4Merge(block, usrBlock, -1, 1) == 1) return;
        if(block.location <= 19)
            if(check4Merge(block, usrBlock, 1, 5) == 1) return;
        if(block.location >= 1 && block.location <= 20)
            if(check4Merge(block, usrBlock, -1, 4) == 1) return;
        if(block.location <= 19)
            if(check4Merge(block, usrBlock, 5, 4) == 1) return;
        if(block.location <= 18)
            if(check4Merge(block, usrBlock, 5, 6) == 1) return;
        if (block.location <= 14)
            if(check4Merge(block, usrBlock, 5, 10) == 1) return;
        if(block.location >= 10)
            if(check4Merge(block, usrBlock, -5, -10) == 1) return;
        if(block.location <= 22)
            if(check4Merge(block, usrBlock, 1, 2) == 1) return;
        if(block.location >= 2)
            if(check4Merge(block, usrBlock, -2, -1) == 1) return;
        if(block.location <= 18)
            if(check4Merge(block, usrBlock, 1, 6) == 1) return;
        if(block.location <= 19 && block.location >= 1)
            if(check4Merge(block, usrBlock, -1, 5) == 1) return;
        if(block.location >= 5 && block.location <= 23)
            if(check4Merge(block, usrBlock, -5, 1) == 1) return;
    }
}