import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Window
 * Sets up window and all gui elements
 * KNOWN BUGS:
 * - Help button: won't sense usr clicking where the interaction panel is, must
 *   click above the score label for the images to go through
 * 
 * @author Tori Postler
 * @version 0.3.8
 */

class Window extends JFrame implements ActionListener, MouseListener {
    /** 
     * TO DO:
     * - get mouse listener to detect where things are, so when hovering over an
     *   obj it will print the obj's id (hover over tree, tree will be printed)
     */
    /* Variables */

    /* Dimensions */
    int W_WIDTH  = 700;
    int W_HEIGHT = 850;
    
    /* Different Swing Components */
    JButton quitBtn, resetBtn, helpBtn;
    JLabel  helpInstructions;

    Image icon = null;

    // Panel that handles the actual game, in the future I want to change this
    // to be a layered pane so I can stop the help button glitch from happening
    InteractionPanel interactionPanel;
    
    /* Variables reffering to help button */
    boolean helpOn      = false; // if button clicked then mouselistener will listen
    int     helpPicture = 0;     // what help pic is being displayed
    
    /**
     * Window
     * create whole gui: buttons, labels... etc..
     */
    Window() {
        /** Adding buttons, labels, slider */
        addBtns();
        addLabels();

        /* Adding the panel where the actual game takes place */
        interactionPanel = new InteractionPanel();
        this.add(interactionPanel);
        
        // adding mouse listener so the help button will work
        // there is still a glitch.
        this.addMouseListener(this);

        /* Setting up window */
        this.setTitle("Triple Town");
        this.setSize(W_WIDTH, W_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);   // i'm sorry Mr.Beckwith, but I tried to add
                                // a layout manager and it screwed everything up
        this.getContentPane().setBackground(new Color(0x416947));
        this.setVisible(true);
        this.setLocationRelativeTo(null);  // sets window in center of screen
    }
    /**
     * addBtns
     * creates and adds all buttons to window, quick shout to code raunch for
     * heling me set an image to button (https://coderanch.com/t/555039/java/Image-button)
     */
    public void addBtns() {
        
        /* reset -> resets the board */
        resetBtn = new JButton();
        try {
            icon = ImageIO.read(this.getClass().getResourceAsStream("/Users/toripos/Desktop/CS3500/Town-Merge/scr/Images/reset.png"));
        }
        catch(Exception e) {
            System.out.println("error caught");
        }
        resetBtn.setIcon(new ImageIcon(icon) );
        resetBtn.setBorderPainted(false);
        resetBtn.setFocusPainted(false);
        resetBtn.setContentAreaFilled(false);
        resetBtn.setBounds(555, 10, 100, 45);
        resetBtn.addActionListener(this);
        resetBtn.setActionCommand("resetButton");
        this.add(resetBtn);
        
        /* quit -> exists program */
        quitBtn = new JButton();
        try {
            icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/quit.png"));
        }
        catch(Exception e) {}
        quitBtn.setIcon(new ImageIcon(icon));
        quitBtn.setBorderPainted(false);
        quitBtn.setFocusPainted(false);
        quitBtn.setContentAreaFilled(false);
        quitBtn.addActionListener(this);
        quitBtn.setActionCommand("quitButton");
        quitBtn.setBounds(555, 65, 100, 45);
        this.add(quitBtn);

        /* help -> shows quick tutorial how to play game */
        helpBtn = new JButton();
        try {
            icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/help.png"));
        }
        catch(Exception e) {}
        helpBtn.setIcon(new ImageIcon(icon));
        helpBtn.setBorderPainted(false);
        helpBtn.setFocusPainted(false);
        helpBtn.setContentAreaFilled(false);
        helpBtn.addActionListener(this);
        helpBtn.setActionCommand("helpButton");
        helpBtn.setBounds(10, 10, 100, 45);
        this.add(helpBtn);
    }
    /**
     * addLabels
     * creates labels and adds to window
     */
    public void addLabels() {
        /* helpInstructions -> holds images for tutorial (img set in later meth)*/
        helpInstructions = new JLabel("");
        helpInstructions.setIcon(new ImageIcon("Images/help1.png"));
        helpInstructions.setVisible(false);
        helpInstructions.setBounds(160, 200, 400, 360);
        this.add(helpInstructions);
    }
    /**
     * actionPerformed
     * Calls methods when coressponding button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        /* quit button -> exit*/
        if (e.getActionCommand().equals("quitButton"))
            System.exit(0);
        // reset button -> populate(true) (true to wipe board)*/
        if (e.getActionCommand().equals("resetButton")) {
            interactionPanel.scoreLabel.setText("Score: 0");
            interactionPanel.populate(true);
        }
        /* help button -> helpOn = true -> mouseListener on, disabled -> mouse
           listener in interactions panel off */
        if(e.getActionCommand().equals("helpButton")) {
            helpOn = true;
            interactionPanel.setEnabled(false);
            interactionPanel.disabled = true;
        }
    }
    /**
     * stateChanged
     * gets where the slider is moved to and sends to interaction panel which
     * deals with changing the size of the objs
     */
    /*
    public void stateChanged(ChangeEvent e) {
        interactionPanel.changeSize(sizeSlider.getValue());
    }
    */
    /**
     * mousePressed-mouseClicked all relate to MouseListen implentation
     */
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    /**
     * mouseClicked
     * Called with usr clicks, when the help button is pressed this is execute
     * changing the image that is shown relating to the tutorial
     * 
     * KNOWN BUG:
     * Can only click above he score label becuase for some reason it doesn't
     * like when usr clicks on interactionsPanel...
     * Possible fixes:
     * - layered pane...
     */
    public void mouseClicked(MouseEvent e) {
        /* helpOn = true when help button pressed */
        if(helpOn) {
            helpInstructions.setVisible(true); // showing the label
            helpPicture++;

            // when reach end of images 
            if(helpPicture == 7)
            {
                helpInstructions.setVisible(false); // hide images
                helpPicture = 0;                    // reset for later use
                helpOn      = false;
                interactionPanel.disabled = false;  // interactionPanel mouseListen on
                return;
            }
            /* changes image relating to what frame/img on */
            switch(helpPicture) {
                case 2:
                try {
                    icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/help2.png"));
                }
                catch(Exception ex) {}
                helpInstructions.setIcon(new ImageIcon(icon) );
                break;

                case 3:
                try {
                    icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/help3.png"));
                }
                catch(Exception ex) {}
                helpInstructions.setIcon(new ImageIcon(icon) );
                break;

                case 4:
                try {
                    icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/help4.png"));
                }
                catch(Exception ex) {}
                helpInstructions.setIcon(new ImageIcon(icon) );
                break;

                case 5:
                try {
                    icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/help5.png"));
                }
                catch(Exception ex) {}
                helpInstructions.setIcon(new ImageIcon(icon) );
                break;

                case 6:
                try {
                    icon = ImageIO.read(this.getClass().getResourceAsStream("Resources/Images/help6.png"));
                }
                catch(Exception ex) {}
                helpInstructions.setIcon(new ImageIcon(icon) );
                break;

                default: break;
            }
        }
     }
}