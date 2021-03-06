/*
 * TCSS 305 � Winter 2015
 * Assignment 6 - Tetris
 */

package zelda;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * The images and animation steps of a guard.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 12 March 2015
 */
public enum Guard {
    
    /** Guard image in the animation cycle. */
    GUARD_1("/images/guard_1.gif"),
    /** Guard image in the animation cycle. */
    GUARD_2("/images/guard_2.gif"),
    /** Guard image in the animation cycle. */
    GUARD_3("/images/guard_3.gif"),
    /** Guard image in the animation cycle. */
    GUARD_4("/images/guard_4.gif");
   
    /** The image of the guard's current step in the animation. */
    private Image myImage;
    
    
    /**
     * Builds the image from the file path to attach to the guard.
     * 
     * @param theImagePath The file path.
     */
    Guard(final String theImagePath) {
        java.net.URL imgURL = getClass().getResource(theImagePath);
        //System.out.println(imgURL);
        myImage = new ImageIcon(imgURL).getImage();
    }
    
    
    /**
     * Returns the image of the Guard.
     * 
     * @return The image of the guard.
     */
    public Image getImage() {
        return myImage;
    }
    
    
    /**
     * Cycles the guards through their animation order.
     * 
     * @return The current guard image.
     */
    public Guard advance() {
        Guard result = GUARD_1;

        switch (this) {
            case GUARD_1:
                result = GUARD_2;
                break;

            case GUARD_2:
                result = GUARD_3;
                break;

            case GUARD_3:
                result = GUARD_4;
                break;
                
            case GUARD_4:
                result = GUARD_1;
                break;

            default:
                break;
                
        }

        return result;
    } 
    
}