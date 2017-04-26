/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package zelda;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * The images of each type of rupee.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 12 March 2015
 */
public enum Rupee {

    /** The image for the green rupee. */
    RUPEE_GREEN("/images/rupee_green.gif"),
    /** The image for the blue rupee. */
    RUPEE_BLUE("/images/rupee_blue.gif"),
    /** The image for the red rupee. */
    RUPEE_RED("/images/rupee_red.gif"),
    /** The image for the purple rupee. */
    RUPEE_PURPLE("/images/rupee_purple.gif");

    /** Case number 3. */
    private static final int THREE = 3;
    /** Case number 4. */
    private static final int FOUR = 4;
    
    /** The image of the rupee. */
    private Image myImage;

    
    /**
     * Attaches the image to the rupee.
     * 
     * @param theImagePath The file name path.
     */
    Rupee(final String theImagePath) {
        java.net.URL imgURL = getClass().getResource(theImagePath);
        //System.out.println(imgURL);
        myImage = new ImageIcon(imgURL).getImage();
    }


    /**
     * Returns the image of the rupee.
     * 
     * @return The image of the rupee.
     */
    public Image getImage() {
        return myImage;
    }


    /**
     * Returns the rupee associated with the int value of
     * 1 through 4.
     * 
     * @param theValue The int value.
     * @return The rupee.
     */
    public Rupee getRupee(final int theValue) {
        Rupee result = RUPEE_GREEN;

        switch (theValue) {
            case 1:
                result = RUPEE_GREEN;
                break;

            case 2:
                result = RUPEE_BLUE;
                break;

            case THREE:
                result = RUPEE_RED;
                break;

            case FOUR:
                result = RUPEE_PURPLE;
                break;

            default:
        }

        return result;
    } 

}