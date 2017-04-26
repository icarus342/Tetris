/*
 * TCSS 305 – Winter 2015
 * Assignment 6 - Tetris
 */

package zelda;

import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * The images and animation steps of a skeleton.
 * 
 * @author Justin Arnett (jarnett@uw.edu)
 * @version 12 March 2015
 */
public enum Skelly {
    
    /** Skelly image in the animation cycle. */
    SKELLY_1("/images/skelly_1.gif"),
    /** Skelly image in the animation cycle. */
    SKELLY_2("/images/skelly_2.gif");
   
    /** The image of the skeleton's current step in the animation. */
    private Image myImage;
    
    /**
     * Builds the image from the file path to attach to the skeleton.
     * 
     * @param theImagePath The file path.
     */
    Skelly(final String theImagePath) {
        java.net.URL imgURL = getClass().getResource(theImagePath);
        //System.out.println(imgURL);
        myImage = new ImageIcon(imgURL).getImage();
    }
    
    
    /**
     * Returns the image of the skeleton.
     * 
     * @return The image of the skeleton.
     */
    public Image getImage() {
        return myImage;
    }
    
    
    /**
     * Cycles the skeletons through their animation order.
     * 
     * @return The current guard image.
     */
    public Skelly advance() {
        Skelly result = SKELLY_1;

        switch (this) {
            case SKELLY_1:
                result = SKELLY_2;
                break;

            case SKELLY_2:
                result = SKELLY_1;
                break;

            default:
        }

        return result;
    } 
    
}